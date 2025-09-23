package com.wserp.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignedEvent {
    private String taskTitle;
    private String projectTitle;
    private String userName;
    private String userEmail;
    private String assignedBy;
}
