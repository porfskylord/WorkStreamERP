package com.wserp.userservice.service.impl;

import com.wserp.commondto.AuthUserDto;
import com.wserp.commondto.UserResponseDto;
import com.wserp.userservice.entity.Users;
import com.wserp.userservice.entity.mappers.UserMapper;
import com.wserp.userservice.repository.UserRepository;
import com.wserp.userservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserResponseDto getUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toUserDto(user);
    }

    @Override
    public AuthUserDto getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toAuthUserDto(user);
    }
}
