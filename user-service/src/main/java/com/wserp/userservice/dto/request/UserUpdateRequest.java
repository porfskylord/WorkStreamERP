package com.wserp.userservice.dto.request;

import com.wserp.userservice.entity.UserDetails;
import com.wserp.userservice.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Role is required")
    private Role role;
    @NotBlank(message = "User details is required")
    private UserDetails userDetails;
}
