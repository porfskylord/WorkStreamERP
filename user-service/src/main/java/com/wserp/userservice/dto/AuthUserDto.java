package com.wserp.userservice.dto;

import com.wserp.userservice.entity.enums.Role;
import lombok.Data;

@Data
public class AuthUserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}
