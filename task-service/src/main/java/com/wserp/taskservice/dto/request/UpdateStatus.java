package com.wserp.taskservice.dto.request;

import com.wserp.taskservice.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStatus {
    @NotBlank(message = "Status is required")
    private TaskStatus status;
}
