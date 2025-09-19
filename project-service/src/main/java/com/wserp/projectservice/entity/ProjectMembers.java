package com.wserp.projectservice.entity;

import com.wserp.projectservice.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "erp_projectmembers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMembers extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;
    @Column(name = "user_id",nullable = false,unique = true)
    private String userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
