package com.wserp.taskservice.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomPrincipal {
    private String userId;
    private String username;
    private String email;
}
