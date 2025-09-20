package com.wserp.projectservice.entity;

import com.wserp.models.BaseEntity;
import com.wserp.projectservice.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "erp_projects")
@Table(indexes = {
        @Index(name = "uk_project_name", columnList = "name", unique = true),
        @Index(name = "idx_project_client", columnList = "client_id"),
        @Index(name = "idx_project_status", columnList = "status"),
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    private String description;

    @Column(nullable = false)
    private ProjectStatus status;

    @Column(nullable = false)
    private String clientId;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMembers> projectMembers;

}
