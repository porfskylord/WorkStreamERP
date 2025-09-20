package com.wserp.projectservice.repository;

import com.wserp.projectservice.entity.ProjectMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMembersRepository extends JpaRepository<ProjectMembers, Long> {

    List<ProjectMembers> findAllByProjectId(String projectId);

    Optional<ProjectMembers> findByProjectIdAndUserId(String id, String memberId);
}
