package com.wserp.projectservice.repository;

import com.wserp.projectservice.entity.Project;
import com.wserp.projectservice.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Project> findAllByClientId(String clientId);
    Optional<Project> findProjectByName(String name);

    List<Project> findAllByClientIdAndStatus(String clientId, ProjectStatus projectStatus);

    List<Project> findAllByStatus(ProjectStatus projectStatus);
}
