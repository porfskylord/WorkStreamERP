package com.wserp.taskservice.dto;

import com.wserp.taskservice.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskDto {
    private String id;
    private String title;
    private String description;
    private String projectId;
    private String assignedTo;
    private TaskStatus status;
    private LocalDateTime deadline;
    private String priority;
    private Double rate;
    private String createdBy;
}
