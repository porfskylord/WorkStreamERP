package com.wserp.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteEvent {
    private String email;
    private String name;
    private String orgName;
    private String inviteBy;
    private String role;
    private String inviteUlr;
}
