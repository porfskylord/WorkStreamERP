package com.wserp.projectservice.repository;

import com.wserp.projectservice.entity.Project;
import com.wserp.projectservice.entity.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Project> findAllByClientId(String clientId);

    Optional<Project> findProjectByName(String name);

    @Query("SELECT p FROM erp_projects p WHERE " +
            "(:clientId IS NULL OR p.clientId = :clientId) AND " +
            "(:status IS NULL OR p.status = :status)")
    List<Project> findProjectsByFilters(
            @Param("clientId") String clientId,
            @Param("status") ProjectStatus status
    );
}
