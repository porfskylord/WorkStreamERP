package com.wserp.userservice.entity;

import com.wserp.models.BaseEntity;
import com.wserp.userservice.entity.enums.Role;
import com.wserp.userservice.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity(name = "sec_users")
@Table(indexes = {
        @Index(name = "idx_users_username", columnList = "username", unique = true),
        @Index(name = "idx_users_email", columnList = "email", unique = true),
        @Index(name = "idx_users_role", columnList = "role", unique = false),
        @Index(name = "idx_users_status", columnList = "status")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private UserDetails userDetails;


}