package com.wserp.userservice.dto.request;

import com.wserp.userservice.entity.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatus {
    @NotBlank(message = "UserStatus is required")
    private UserStatus userStatus;
}
