package com.wserp.inviteservice.entity;

import com.wserp.common.enums.Role;
import com.wserp.inviteservice.entity.enums.InviteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity(name = "erp_invite")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invite {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    @Column(nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String orgId;

    @Column(nullable = false)
    private String orgName;

    @Column(nullable = false)
    private String inviteBy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    @Column(nullable = false)
    private String inviteToken;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @Column(nullable = false)
    private LocalDateTime expiredAt;


    private LocalDateTime acceptedAt;

}
