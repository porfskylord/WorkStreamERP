package com.wserp.userservice.entity;

import com.wserp.userservice.entity.enums.Active;
import com.wserp.userservice.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity(name = "sec_users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Active active;

    @Embedded
    private UserDetails userDetails;


}