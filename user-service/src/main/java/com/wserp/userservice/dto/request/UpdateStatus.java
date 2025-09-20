package com.wserp.userservice.dto.request;

import com.wserp.userservice.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStatus {
    @NotBlank(message = "Status is required")
    private Status status;
}
