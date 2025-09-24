package com.wserp.inviteservice.service;

import com.wserp.common.dto.AcceptInviteRequest;
import com.wserp.common.dto.InviteEvent;
import com.wserp.common.jwt.CurrentUserData;
import com.wserp.common.proto.*;
import com.wserp.inviteservice.dto.request.InviteRequest;
import com.wserp.inviteservice.entity.Invite;
import com.wserp.inviteservice.entity.enums.InviteStatus;
import com.wserp.inviteservice.repository.InviteRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserData currentUserData;
    private final OrgMemberServiceGrpc.OrgMemberServiceBlockingStub orgMemberServiceBlockingStub;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Value("${app.invite-url}")
    private String baseInviteUrl;


    public String createInvite(InviteRequest inviteRequest) {

        OrgMemberRequest orgMemberRequest = OrgMemberRequest.newBuilder()
                .setOrgId(inviteRequest.getOrgId())
                .setUserId(currentUserData.getCurrentUserId())
                .build();

        OrgMemberResponse orgMemberResponse = orgMemberServiceBlockingStub.getOrgMember(orgMemberRequest);

        if (orgMemberResponse == null) {
            throw new BadRequestException("Invalid organization or user is not a member of the organization");
        }

        String inviteToken = UUID.randomUUID().toString();

        Invite invite = Invite.builder()
                .email(inviteRequest.getEmail())
                .name(inviteRequest.getName())
                .orgId(inviteRequest.getOrgId())
                .orgName(orgMemberResponse.getOrgName())
                .inviteBy(currentUserData.getCurrentUserId())
                .role(inviteRequest.getRole())
                .status(InviteStatus.PENDING)
                .title(inviteRequest.getTitle())
                .inviteToken(passwordEncoder.encode(inviteToken))
                .expiredAt(LocalDateTime.now().plusDays(7))
                .build();


        Invite savedInvite = inviteRepository.save(invite);

        String inviteUrl = baseInviteUrl + inviteToken + "/accept";

        InviteEvent inviteEvent = InviteEvent.builder()
                .email(savedInvite.getEmail())
                .name(savedInvite.getName())
                .orgName(orgMemberResponse.getOrgName())
                .inviteBy(currentUserData.getCurrentUsername())
                .role(savedInvite.getRole().name())
                .inviteUlr(inviteUrl)
                .build();

        kafkaTemplate.send("invite-topic", inviteEvent);

        return "Invite created successfully";

    }

    public Map<String, String> acceptInvite(String token, AcceptInviteRequest acceptInviteRequest) {
        List<Invite> pendingInvites = inviteRepository.findByStatus(InviteStatus.PENDING);

        Invite validInvite = pendingInvites.stream()
                .filter(invite -> LocalDateTime.now().isBefore(invite.getExpiredAt()))
                .filter(invite -> passwordEncoder.matches(token, invite.getInviteToken()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Invalid or expired invite token"));

        if (validInvite.getStatus() == InviteStatus.ACCEPTED) {
            throw new BadRequestException("This invite has already been accepted");
        }

        validInvite.setStatus(InviteStatus.ACCEPTED);
        validInvite.setAcceptedAt(LocalDateTime.now());
        inviteRepository.save(validInvite);

        CreateUserByInviteRequest createUserByInviteRequest = CreateUserByInviteRequest.newBuilder()
                .setName(validInvite.getName())
                .setEmail(validInvite.getEmail())
                .setPassword(acceptInviteRequest.getPassword())
                .setRole(validInvite.getRole().name())
                .setOrgId(validInvite.getOrgId())
                .setOrgName(validInvite.getOrgName())
                .setTitle(validInvite.getTitle())
                .build();

        CreateUserResponse userResponse = userServiceBlockingStub.createUserByInvite(createUserByInviteRequest);


        return Map.of(
                "message", "Invite accepted successfully",
                "userId", userResponse.getId(),
                "username", userResponse.getUsername(),
                "role", userResponse.getRole()
        );
    }
}
