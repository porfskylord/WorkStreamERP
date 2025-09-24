package com.wserp.inviteservice.dto.request;

import com.wserp.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteRequest {
    private String orgId;
    private String email;
    private String name;
    private Role role;
    private String title;
}
