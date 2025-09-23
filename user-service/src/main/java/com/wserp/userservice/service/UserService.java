package com.wserp.userservice.service;


import com.wserp.common.jwt.CurrentUserData;
import com.wserp.userservice.repository.UserRepository;
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


//    public Users getUserById(String id) {
//        return userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
//    }
//
//    public Users getUserByUsername(String username) {
//        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
//    }
//
//    public Users getUserByEmail(String email) {
//        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
//    }
//
//    @Transactional
//    public Users updateUser(String id, UserUpdateRequest request) {
//        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
//        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//            throw new ConfilictException(ErrorMessage.EMAIL_ALREADY_EXISTS.getMessage());
//        }
//        user.setRole(request.getRole());
//        user.setEmail(request.getEmail());
//        user.setUserDetails(request.getUserDetails());
//        user.setUpdatedBy(currentUserData.getCurrentUserId());
//        return userRepository.save(user);
//
//    }
//
//    @Transactional
//    public void deleteUser(String id) {
//        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
//        user.setUserStatus(UserStatus.DELETED);
//        user.setDeletedBy(currentUserData.getCurrentUserId());
//        user.setDeleted(true);
//        userRepository.save(user);
//    }
//
//    public Users updateUserStatus(String id, UpdateStatus updateStatus) {
//        Users user = userRepository.findUserById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
//        user.setUserStatus(updateStatus.getUserStatus());
//        user.setUpdatedBy(currentUserData.getCurrentUserId());
//        return userRepository.save(user);
//    }
//
//    public List<Users> getUsers(String role, String userStatus, String search) {
//        UserStatus statusEnum = null;
//        if (userStatus != null) {
//            try {
//                statusEnum = UserStatus.valueOf(userStatus.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                throw new BadRequestException("Invalid userStatus value");
//            }
//        }
//        return userRepository.findUsersByFilters(role, statusEnum, search);
//    }


}
