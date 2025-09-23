package com.wserp.taskservice.entity;

import com.wserp.models.BaseEntity;
import com.wserp.taskservice.entity.enums.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity(name = "erp_tasks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private TaskStatus status;
    @Column(nullable = false)
    private LocalDateTime deadline;
    private String priority;
    @Column(nullable = false)
    private Double rate;
    @Column(nullable = false)
    private String projectId;
    @Column(nullable = false)
    private String projectManagerId;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskAssignees> taskAssignees = new ArrayList<>();
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskComments> comments = new ArrayList<>();
}
