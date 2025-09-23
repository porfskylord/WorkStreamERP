package com.wserp.projectservice.dto.request;

import com.wserp.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddProjectMembers {
    @NotBlank(message = "User id is required")
    private String userId;
    @NotBlank(message = "Role is required")
    private Role role;
}
