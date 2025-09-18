package com.wserp.userservice.repository;

import com.wserp.userservice.entity.Users;
import com.wserp.userservice.entity.enums.Active;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    List<Users> findAllByActive(Active active);
    Optional<Users> findUserById(String id);
}
