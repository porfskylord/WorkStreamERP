package com.wserp.orgmembersservice.entity;


import com.wserp.common.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity(name = "erp_orgmembers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgMembers {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(nullable = false)
    private String orgId;

    @Column(nullable = false)
    private String orgName;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private String title;

    @Column(nullable = false)
    private String invitedBy;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
