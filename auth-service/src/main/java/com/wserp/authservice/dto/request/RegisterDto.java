package com.wserp.authservice.dto.request;

import com.wserp.authservice.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    private String id;
    private String username;
    private String email;
    private Role role;
}
