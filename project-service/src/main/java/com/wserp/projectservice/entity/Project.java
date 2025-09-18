package com.wserp.projectservice.entity;

import com.wserp.projectservice.entity.enums.ProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "erp_projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends BaseEntity{
    @Column(nullable = false,unique = true)
    private String name;
    private String description;

    @Column(nullable = false)
    private ProjectStatus status;

    @Column(nullable = false)
    private String clientId;

    private String clientName;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMembers> projectMembers;
}
