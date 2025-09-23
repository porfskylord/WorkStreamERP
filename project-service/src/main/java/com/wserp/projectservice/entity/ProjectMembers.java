package com.wserp.projectservice.entity;

import com.wserp.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity(name = "erp_projectmembers")
@Table(indexes = {
        @Index(name = "idx_projectmembers_project", columnList = "project_id"),
        @Index(name = "idx_projectmembers_user", columnList = "user_id"),
        @Index(name = "idx_projectmembers_role", columnList = "role"),})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(name = "added_by", nullable = false)
    private String addedBy;

    @Column(name = "added_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime addedAt;
}
