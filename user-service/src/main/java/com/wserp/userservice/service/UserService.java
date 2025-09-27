package com.wserp.userservice.service;


import com.wserp.common.enums.ErrorMessage;
import com.wserp.common.exception.ConfilictException;
import com.wserp.common.exception.NotFoundException;
import com.wserp.common.jwt.CurrentUserData;
import com.wserp.userservice.dto.request.UpdateStatus;
import com.wserp.userservice.dto.request.UpdateUserRequest;
import com.wserp.userservice.entity.Users;
import com.wserp.userservice.entity.enums.UserStatus;
import com.wserp.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CurrentUserData currentUserData;

    @Value("${app.system-user-id}")
    private String systemUserId;


    public Users getUserById(String id) {
        return userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    @Transactional
    public Object updateUser(String currentUserId, UpdateUserRequest updateUserRequest) {
        Users user = userRepository.findUserById(currentUserId).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        if (userRepository.findByEmail(updateUserRequest.getEmail()).isPresent()) {
            throw new ConfilictException(ErrorMessage.EMAIL_ALREADY_EXISTS.getMessage());
        }

        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setTitle(updateUserRequest.getTitle());
        user.setLocation(updateUserRequest.getLocation());
        user.setBio(updateUserRequest.getBio());
        user.setProfileImage(updateUserRequest.getProfileImage());
        user.setUpdatedBy(currentUserData.getCurrentUserId());
        return userRepository.save(user);
    }


    @Transactional
    public void deleteUser(String id) {
        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
        user.setUserStatus(UserStatus.DELETED);
        user.setDeletedBy(currentUserData.getCurrentUserId());
        user.setDeleted(true);
        userRepository.save(user);
    }

    public Users updateUserStatus(String id, UpdateStatus updateStatus) {
        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
        user.setUserStatus(updateStatus.getUserStatus());
        user.setUpdatedBy(currentUserData.getCurrentUserId());
        return userRepository.save(user);
    }


}
