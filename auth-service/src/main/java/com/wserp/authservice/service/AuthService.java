package com.wserp.authservice.service;

import com.wserp.authservice.dto.TokenDto;
import com.wserp.authservice.dto.request.LoginRequest;
import com.wserp.authservice.utils.JwtUtils;
import com.wserp.common.dto.OrgRegistrationResponse;
import com.wserp.common.dto.OrganizationRegistrationRequest;
import com.wserp.common.exception.WrongCredentialsException;
import com.wserp.common.proto.UserRequest;
import com.wserp.common.proto.UserResponse;
import com.wserp.common.proto.UserServiceGrpc;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public TokenDto login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                return generateToken(request.getUsername());
            }
            throw new WrongCredentialsException("Authentication failed");
        } catch (Exception e) {
            throw new WrongCredentialsException("Invalid username or password");
        }
    }

    public TokenDto refreshToken(String token) {
        if (jwtUtils.validateToken(token)) {
            String username = jwtUtils.extractUsername(token);
            return generateToken(username);
        }
        throw new JwtException("Invalid token");
    }

    public OrgRegistrationResponse register(@Valid OrganizationRegistrationRequest request) {
        UserRequest userRequest = UserRequest.newBuilder()
                .setName(request.getName())
                .setEmail(request.getEmail())
                .setOrganizationName(request.getOrganizationName())
                .setPassword(request.getPassword())
                .build();

        UserResponse userResponse = userServiceBlockingStub.createUser(userRequest);

        return OrgRegistrationResponse.builder()
                .message("User created successfully")
                .username(userResponse.getUsername())
                .userId(userResponse.getId())
                .orgId(userResponse.getOrganizationId())
                .build();

    }


    private TokenDto generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime accessExpiry = now.plus(jwtUtils.getAccessTokenExpirationInMillis(),
                java.time.temporal.ChronoUnit.MILLIS);
        LocalDateTime refreshExpiry = now.plus(jwtUtils.getRefreshTokenExpirationInMillis(),
                java.time.temporal.ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return TokenDto.builder()
                .accessToken(jwtUtils.generateAccessToken(username))
                .refreshToken(jwtUtils.generateRefreshToken(username))
                .accessExpirationTime(accessExpiry.format(formatter))
                .refreshExpirationTime(refreshExpiry.format(formatter))
                .build();
    }
}