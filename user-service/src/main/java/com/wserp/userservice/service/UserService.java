package com.wserp.userservice.service;

import com.wserp.enums.ErrorMessage;
import com.wserp.userservice.dto.request.RegisterRequest;
import com.wserp.userservice.dto.request.UpdateStatus;
import com.wserp.userservice.dto.request.UserUpdateRequest;
import com.wserp.userservice.entity.Users;
import com.wserp.userservice.entity.enums.Status;
import com.wserp.userservice.exeption.ConfilictException;
import com.wserp.userservice.exeption.NotFoundException;
import com.wserp.userservice.filter.CurrentUserData;
import com.wserp.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserData currentUserData;

    @Value("${app.system-user-id}")
    private String systemUserId;

    @Transactional
    public Users saveUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ConfilictException(ErrorMessage.USERNAME_ALREADY_EXISTS.getMessage());
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConfilictException(ErrorMessage.EMAIL_ALREADY_EXISTS.getMessage());
        }
        Users newUser = Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole())
                .status(Status.ACTIVE)
                .build();
        newUser.setCreatedBy(systemUserId);
        newUser.setUpdatedBy(systemUserId);

        return userRepository.save(newUser);
    }


    public Users getUserById(String id) {
        return userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    @Transactional
    public Users updateUser(String id, UserUpdateRequest request) {
        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConfilictException(ErrorMessage.EMAIL_ALREADY_EXISTS.getMessage());
        }
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setUserDetails(request.getUserDetails());
        user.setUpdatedBy(currentUserData.getCurrentUserId());
        return userRepository.save(user);

    }

    @Transactional
    public void deleteUser(String id) {
        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
        user.setStatus(Status.DELETED);
        user.setDeletedBy(currentUserData.getCurrentUserId());
        user.setDeleted(true);
        userRepository.save(user);
    }

    public Users updateUserStatus(String id, UpdateStatus updateStatus) {
        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
        user.setStatus(updateStatus.getStatus());
        user.setUpdatedBy(currentUserData.getCurrentUserId());
        return userRepository.save(user);
    }

    public List<Users> getUsers(String role, String status, String search) {
        Status statusEnum = null;
        if (status != null) {
            try {
                statusEnum = Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid status value");
            }
        }
        return userRepository.findUsersByFilters(role, statusEnum, search);
    }
}
