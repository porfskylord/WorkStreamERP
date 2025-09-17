package com.wserp.authservice.service;

import com.wserp.authservice.client.UserServiceClient;
import com.wserp.authservice.dto.TokenDto;
import com.wserp.authservice.dto.request.LoginRequest;
import com.wserp.authservice.dto.request.RegisterDto;
import com.wserp.authservice.dto.request.RegisterRequest;
import com.wserp.authservice.exception.WrongCredentialsException;
import com.wserp.authservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final UserServiceClient userServiceClient;
    private final AuthenticationManager authenticationManager;

    public TokenDto login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                return TokenDto.builder()
                        .token(jwtUtils.generateToken(request.getUsername()))
                        .build();
            }
            throw new WrongCredentialsException("Authentication failed");
        } catch (Exception e) {
            throw new WrongCredentialsException("Invalid username or password");
        }
    }

    public RegisterDto register(RegisterRequest request){
        return userServiceClient.saveUser(request).getBody();
    }
}