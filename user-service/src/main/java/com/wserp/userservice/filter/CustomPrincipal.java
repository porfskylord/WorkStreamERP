package com.wserp.userservice.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CustomPrincipal {
    private String userId;
    private String username;
    private String email;
}
