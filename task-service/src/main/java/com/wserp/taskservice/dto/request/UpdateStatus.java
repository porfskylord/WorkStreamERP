package com.wserp.taskservice.dto;

import com.wserp.taskservice.entity.enums.TaskStatus;
import lombok.Data;

@Data
public class UpdateStatus {
    private TaskStatus status;
}
