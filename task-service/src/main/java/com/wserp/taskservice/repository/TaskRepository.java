package com.wserp.taskservice.repository;

import com.wserp.taskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    Optional<Task> findById(String id);

    Optional<Task> findByTitle(String title);

    Optional<Task> findBYAssignedTo(String userId);

    List<Task> findBYProjectId(String projectId);

    List<Task> findBYCreatedBy(String createdBy);

    Optional<Task> findBYStatus(String status);
}
