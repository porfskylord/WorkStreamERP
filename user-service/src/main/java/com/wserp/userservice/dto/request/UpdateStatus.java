package com.wserp.userservice.dto.request;

import com.wserp.userservice.entity.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStatus {
    @NotBlank(message = "UserStatus is required")
    private UserStatus userStatus;
}
