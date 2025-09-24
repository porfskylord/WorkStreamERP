package com.wserp.orgmembersservice.service;

import com.wserp.common.dto.OrgMembersEvent;
import com.wserp.orgmembersservice.entity.OrgMembers;
import com.wserp.orgmembersservice.repository.OrgMembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgMembersService {

    private final OrgMembersRepository orgMembersRepositroy;

    @Value("${app.system-user-id}")
    private String systemUserId;


    public void saveOrgMembers(OrgMembersEvent request) {
        OrgMembers orgMembers = OrgMembers.builder()
                .orgId(request.getOrgId())
                .userId(request.getUserId())
                .orgName(request.getOrgName())
                .userName(request.getUserName())
                .role(request.getRole())
                .title(request.getTitle())
                .invitedBy(systemUserId)
                .build();

        OrgMembers savedOrgMembers = orgMembersRepositroy.save(orgMembers);
    }
}
