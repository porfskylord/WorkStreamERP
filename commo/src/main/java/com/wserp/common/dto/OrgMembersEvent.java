package com.wserp.common.dto;

import com.wserp.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgMembersEvent {
    private String orgId;
    private String orgName;
    private String userId;
    private String userName;
    private Role role;
    private String title;
}
