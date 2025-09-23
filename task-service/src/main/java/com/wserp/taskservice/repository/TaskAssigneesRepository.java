package com.wserp.taskservice.repository;

import com.wserp.taskservice.entity.TaskAssignees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssigneesRepository extends JpaRepository<TaskAssignees, Long> {
}
