package com.wserp.userservice.dto.request;

import com.wserp.common.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Role is required")
    private Role role;
}
