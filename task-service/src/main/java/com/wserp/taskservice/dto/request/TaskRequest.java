package com.wserp.taskservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Project id is required")
    private String projectId;
    @NotBlank(message = "Assigned to is required")
    private List<String> assignedTo;
    @NotBlank(message = "Deadline is required")
    private LocalDateTime deadline;
    @NotBlank(message = "Priority is required")
    private String priority;
    @NotBlank(message = "Rate is required")
    private Double rate;
}
