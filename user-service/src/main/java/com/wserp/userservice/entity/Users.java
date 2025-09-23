package com.wserp.userservice.entity;

import com.wserp.common.enums.Role;
import com.wserp.common.models.BaseEntity;
import com.wserp.userservice.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity(name = "sec_users")
@Table(indexes = {
        @Index(name = "idx_users_username", columnList = "username", unique = true),
        @Index(name = "idx_users_email", columnList = "email", unique = true),
        @Index(name = "idx_users_status", columnList = "userStatus")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity {

    @Column(nullable = false, unique = true)
    protected String username;
    @Column(nullable = false)
    protected String password;
    @Column(nullable = false, unique = true)
    protected String email;
    @Enumerated(EnumType.STRING)
    protected UserStatus userStatus;
    @Column(nullable = false)
    private String name;
    private String title;
    private String location;
    private String bio;
    private String profileImage;
    private Role role;

}