package com.wserp.projectservice.repository;

import com.wserp.projectservice.entity.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMembersRepository extends JpaRepository<ProjectMembers, Long> {

    List<ProjectMembers> findAllByProjectId(String projectId);

    Optional<ProjectMembers> findByProjectIdAndUserId(String id, String memberId);

    @Query("SELECT COUNT(pm) > 0 FROM ProjectMembers pm WHERE pm.project.id = :projectId AND pm.userId = :userId")
    boolean existsByProjectIdAndUserId(@Param("projectId") String projectId, @Param("userId") String userId);
}
