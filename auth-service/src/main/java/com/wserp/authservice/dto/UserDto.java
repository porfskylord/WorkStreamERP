package com.wserp.authservice.dto;

import com.wserp.authservice.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private Role role;
}
