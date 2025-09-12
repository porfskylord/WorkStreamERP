package com.wserp.userservice.repository;

import com.wserp.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUserById(Long id);

    Optional<Users> findByEmail(String email);
}
