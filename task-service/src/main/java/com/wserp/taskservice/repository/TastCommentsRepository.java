package com.wserp.taskservice.repository;

import com.wserp.taskservice.entity.TaskComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TastCommentsRepository extends JpaRepository<TaskComments, String> {
}
