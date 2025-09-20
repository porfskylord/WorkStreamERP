package com.wserp.userservice.repository;

import com.wserp.userservice.entity.Users;
import com.wserp.userservice.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    List<Users> findAllByStatus(Status status);

    Optional<Users> findUserById(String id);
    
    @Query("SELECT u FROM sec_users u WHERE " +
           "(:role IS NULL OR u.role = :role) AND " +
           "(:status IS NULL OR u.status = :status) AND " +
           "(:search IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.userDetails.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.userDetails.lastName) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Users> findUsersByFilters(
        @Param("role") String role,
        @Param("status") Status status,
        @Param("search") String search
    );
}
