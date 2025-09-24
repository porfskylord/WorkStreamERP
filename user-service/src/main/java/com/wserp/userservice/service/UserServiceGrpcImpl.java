package com.wserp.userservice.service;


import com.wserp.common.dto.OrgMembersEvent;
import com.wserp.common.enums.Role;
import com.wserp.common.proto.*;
import com.wserp.userservice.entity.Users;
import com.wserp.userservice.entity.enums.UserStatus;
import com.wserp.userservice.repository.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationServiceGrpc.OrganizationServiceBlockingStub organizationServiceBlockingStub;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Value("${app.system-user-id}")
    private String systemUserId;

    @Override
    @Transactional
    public void createUser(UserRequest userRequest, StreamObserver<UserResponse> responseObserver) {
        log.info("Creating user: " + userRequest);
        String username = generateUsername(userRequest.getName());

        if (userRepository.findByUsername(username).isPresent()) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("Username already exists")
                            .asRuntimeException()
            );
            return;
        }
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("Email already exists")
                            .asRuntimeException()
            );
            return;
        }

        Users newUser = Users.builder()
                .name(userRequest.getName())
                .username(username)
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .role(Role.OWNER)
                .build();

        newUser.setCreatedBy(systemUserId);
        newUser.setUpdatedBy(systemUserId);

        Users savedUser = userRepository.save(newUser);

        OrganizationRequest organizationRequest = OrganizationRequest.newBuilder()
                .setName(userRequest.getOrganizationName())
                .setOwnerId(savedUser.getId())
                .build();

        OrganizationResponse organizationResponse = organizationServiceBlockingStub.createOrganization(organizationRequest);

        OrgMembersEvent organizationEvent = OrgMembersEvent.builder()
                .orgId(organizationResponse.getId())
                .orgName(userRequest.getOrganizationName())
                .userId(savedUser.getId())
                .userName(savedUser.getUsername())
                .role(Role.OWNER)
                .title("Owner")
                .build();

        kafkaTemplate.send("add-org-member-topic", organizationEvent);

        UserResponse userResponse = UserResponse.newBuilder()
                .setId(savedUser.getId())
                .setUsername(savedUser.getUsername())
                .setEmail(savedUser.getEmail())
                .setName(savedUser.getName())
                .setOrganizationId(organizationResponse.getId())
                .setRole(savedUser.getRole().name())
                .build();

        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void getUserByUsernameOrEmail(GetUserByUsernameOrEmailRequest request, StreamObserver<AuthUserResponse> responseObserver) {
        try {
            Users user;

            if (request.getUsernameOrEmail().contains("@")) {
                user = userRepository.findByEmail(request.getUsernameOrEmail())
                        .orElseThrow(() -> Status.NOT_FOUND
                                .withDescription("User not found")
                                .asRuntimeException());
            } else {
                user = userRepository.findByUsername(request.getUsernameOrEmail())
                        .orElseThrow(() -> Status.NOT_FOUND
                                .withDescription("User not found")
                                .asRuntimeException());
            }

            AuthUserResponse authUserResponse = AuthUserResponse.newBuilder()
                    .setId(user.getId())
                    .setUsername(user.getUsername())
                    .setEmail(user.getEmail())
                    .setName(user.getName())
                    .setPassword(user.getPassword())
                    .setRole(user.getRole().name())
                    .build();

            responseObserver.onNext(authUserResponse);
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Internal server error")
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }


    private String generateUsername(String fullname) {
        String baseUsername = "wse_" + fullname.trim()
                .replaceAll("\\s+", "_")
                .replaceAll("\\W", "")
                .toLowerCase();
        int maxLength = 20;
        if (baseUsername.length() > maxLength) {
            baseUsername = baseUsername.substring(0, maxLength);
        }

        String username = baseUsername;
        int count = 1;

        while (userRepository.findByUsername(username).isPresent()) {
            String suffix = "_" + count++;
            int allowedLength = maxLength - suffix.length();
            username = baseUsername.substring(0, Math.min(baseUsername.length(), allowedLength)) + suffix;
        }

        return username;
    }

    @Override
    public void createUserByInvite(CreateUserByInviteRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        String username = generateUsername(request.getName());

        if (userRepository.findByUsername(username).isPresent()) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("Username already exists")
                            .asRuntimeException()
            );
            return;
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("Email already exists")
                            .asRuntimeException()
            );
            return;
        }

        Users newUser = Users.builder()
                .name(request.getName())
                .username(username)
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .role(Role.valueOf(request.getRole()))
                .build();

        newUser.setCreatedBy(systemUserId);
        newUser.setUpdatedBy(systemUserId);

        Users savedUser = userRepository.save(newUser);

        OrgMembersEvent organizationEvent = OrgMembersEvent.builder()
                .orgId(request.getOrgId())
                .orgName(request.getOrgName())
                .userId(savedUser.getId())
                .userName(savedUser.getUsername())
                .role(savedUser.getRole())
                .title(request.getTitle())
                .build();

        kafkaTemplate.send("add-org-member-topic", organizationEvent);

        CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
                .setId(savedUser.getId())
                .setUsername(savedUser.getUsername())
                .setRole(savedUser.getRole().name())
                .build();

        responseObserver.onNext(createUserResponse);
        responseObserver.onCompleted();

    }
}

