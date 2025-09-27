package com.wserp.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteDto {
    private String id;
    private String orgId;
    private String orgName;
    private String inviteBy;
    private String userId;
    private String email;
    private String role;
    private String title;
    private String status;
}


