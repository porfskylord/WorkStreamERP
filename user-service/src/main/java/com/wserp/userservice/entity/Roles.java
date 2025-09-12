package com.wserp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wserp.models.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sec_roles")
@EqualsAndHashCode(callSuper = true, exclude = "users")
@ToString(callSuper = true)
public class Roles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Users> users = new HashSet<>();

    // Helper method to manage bidirectional relationship
    public void addUser(Users user) {
        users.add(user);
        user.setRole(this);
    }

    public void removeUser(Users user) {
        users.remove(user);
        user.setRole(null);
    }
}