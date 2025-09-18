package com.wserp.projectservice.dto.request;

import com.wserp.projectservice.entity.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProjectRequest {
    private String name;
    private String description;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
