package com.wserp.authservice.service.impl;

import com.wserp.authservice.client.UserServiceClient;
import com.wserp.authservice.dto.LoginRequestDTO;
import com.wserp.authservice.service.intf.AuthService;
import com.wserp.authservice.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserServiceClient userServiceClient,PasswordEncoder passwordEncoder,JwtUtils jwtUtils) {
        this.userServiceClient = userServiceClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        return userServiceClient.getUserByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u -> jwtUtils.generateToken(u.getEmail(), u.getRole()));
    }

    @Override
    public boolean validateToken(String token) {
        try {
            jwtUtils.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
