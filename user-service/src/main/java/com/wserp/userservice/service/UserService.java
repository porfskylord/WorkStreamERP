package com.wserp.userservice.service;

import com.wserp.userservice.dto.request.RegisterRequest;
import com.wserp.userservice.dto.request.UserUpdateRequest;
import com.wserp.userservice.entity.Users;
import com.wserp.userservice.entity.enums.Active;
import com.wserp.userservice.entity.enums.ErrorMessage;
import com.wserp.userservice.entity.enums.Role;
import com.wserp.userservice.exeption.ConfilictException;
import com.wserp.userservice.exeption.NotFoundException;
import com.wserp.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public Users saveUser(RegisterRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new ConfilictException("Username already exists");
        }

        Users requestUser = Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .active(Active.ACTIVE)
                .build();

        return userRepository.save(requestUser);
    }

    public List<Users> getAll() {
        return userRepository.findAllByActive(Active.ACTIVE);
    }

    public Users getUserById(String id) {
        return findUserById(id);
    }

    public Users getUserByUsername(String username) {
        return findByUsername(username);
    }

    public Users getUserByEmail(String email) {
        return findByEmail(email);
    }

    public Users updateUser(String id, UserUpdateRequest request) {
        Users user = findUserById(id);

        if(request.getUsername() != null){
            user.setUsername(request.getUsername());
        }
        if(request.getPassword() != null){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if(request.getEmail() != null){
            user.setEmail(request.getEmail());
        }
        if(request.getUserDetails() != null){
            user.setUserDetails(request.getUserDetails());
        }
        return userRepository.save(user);

    }

    public void deleteUser(String id) {
        Users user = findUserById(id);
        user.setActive(Active.INACTIVE);
        userRepository.save(user);
    }

    private Users findUserById(String id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    private Users findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

    private Users findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
    }

}
