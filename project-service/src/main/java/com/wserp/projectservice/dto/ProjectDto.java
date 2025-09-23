package com.wserp.projectservice.dto;

import com.wserp.projectservice.entity.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {
    private String id;
    private String name;
    private String description;
    private ProjectStatus status;
    private String managerId;
    private LocalDate startDate;
    private LocalDate endDate;
}
