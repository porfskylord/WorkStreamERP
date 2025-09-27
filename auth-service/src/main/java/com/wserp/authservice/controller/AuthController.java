package com.wserp.authservice.controller;

import com.wserp.authservice.dto.TokenDto;
import com.wserp.authservice.dto.request.LoginRequest;
import com.wserp.authservice.service.AuthService;
import com.wserp.common.dto.OrganizationRegistrationRequest;
import com.wserp.common.exception.InvalidTokenException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthService authService;

    //------------------------------------------------------------For Login-----------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    //----------------------------------------------------------For Register-----------------------------------------------------------------------------------
    @PostMapping("/create-org")
    public ResponseEntity<?> register(@RequestBody @Valid OrganizationRegistrationRequest request) {
        log.info("Registering organization: {}", request);
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        return ResponseEntity.ok(authService.refreshToken(token));

    }

    //----------------------------------------------------------For Test-----------------------------------------------------------------------------------

    //----------------------------------------------------------For Extract Token-----------------------------------------------------------------------------------
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new InvalidTokenException("Invalid token");
    }


}
