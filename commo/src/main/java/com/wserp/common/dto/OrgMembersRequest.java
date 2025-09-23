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
public class OrgMembersRequest {
    private String orgId;
    private String userId;
    private Role role;
    private String title;
}
