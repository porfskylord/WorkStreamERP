package com.wserp.orgmembersservice.service;

import com.wserp.common.dto.OrgMembersRequest;
import com.wserp.orgmembersservice.entity.OrgMembers;
import com.wserp.orgmembersservice.repository.OrgMembersRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgMembersService {

    private final OrgMembersRepositroy orgMembersRepositroy;

    @Value("${app.system-user-id}")
    private String systemUserId;


    public void saveOrgMembers(OrgMembersRequest request) {
        OrgMembers orgMembers = OrgMembers.builder()
                .orgId(request.getOrgId())
                .userId(request.getUserId())
                .role(request.getRole())
                .title(request.getTitle())
                .invitedBy(systemUserId)
                .build();

        orgMembersRepositroy.save(orgMembers);
    }
}
