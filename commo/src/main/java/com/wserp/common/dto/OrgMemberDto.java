package com.wserp.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgMemberDto {
    private String organizationName;
    private String organizationId;
    private String userId;
    private String userName;
    private String userRole;
    private String title;
}
