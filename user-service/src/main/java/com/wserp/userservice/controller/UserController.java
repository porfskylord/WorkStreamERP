package com.wserp.userservice.controller;


import com.wserp.userservice.dto.AuthUserDto;
import com.wserp.userservice.dto.UserDto;
import com.wserp.userservice.dto.request.RegisterRequest;
import com.wserp.userservice.dto.request.UpdateStatus;
import com.wserp.userservice.dto.request.UserUpdateRequest;
import com.wserp.userservice.filter.CurrentUserData;
import com.wserp.userservice.repository.UserRepository;
import com.wserp.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Management")
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CurrentUserData currentUserData;

    @Value("${internal.token}")
    private String internalToken;

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid RegisterRequest request, @RequestHeader(value = "X-INTERNAL-TOKEN", required = false) String token) {
        if (!internalToken.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return ResponseEntity.ok(modelMapper.map(userService.saveUser(request), UserDto.class));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAll(@RequestParam(required = false) String role, @RequestParam(required = false) String status, @RequestParam(required = false) String search) {
        return ResponseEntity.ok(userService.getUsers(role, status, search).stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getMe() {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(currentUserData.getCurrentUserId()), UserDto.class));
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsername(
            @PathVariable String username,
            @RequestHeader(value = "X-INTERNAL-TOKEN", required = false) String token) {

        if (!internalToken.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        AuthUserDto userDto = modelMapper.map(userService.getUserByUsername(username), AuthUserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(userService.updateUser(currentUserData.getCurrentUserId(), request), UserDto.class));
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMe() {
        userService.deleteUser(currentUserData.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> updateUserStatus(@PathVariable String id, @RequestBody UpdateStatus updateStatus) {
        return ResponseEntity.ok(modelMapper.map(userService.updateUserStatus(id, updateStatus), UserDto.class));
    }

}
