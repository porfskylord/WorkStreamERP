package com.wserp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wserp.models.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sec_users")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties("users")
    @ToString.Exclude
    private Roles role;
}