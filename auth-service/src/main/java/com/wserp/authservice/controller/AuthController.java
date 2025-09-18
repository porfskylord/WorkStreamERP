package com.wserp.authservice.controller;

import com.wserp.authservice.dto.TokenDto;
import com.wserp.authservice.dto.request.LoginRequest;
import com.wserp.authservice.dto.request.RegisterDto;
import com.wserp.authservice.dto.request.RegisterRequest;
import com.wserp.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginRequest request){
        log.info("Login request: {} {}", request.getUsername(), request.getPassword());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody @Valid RegisterRequest request){
        log.info("Register request: {} {}", request.getUsername(), request.getPassword());
        return ResponseEntity.ok(authService.register(request));
    }



}
