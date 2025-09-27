package com.wserp.userservice.controller;


import com.wserp.common.dto.UserDto;
import com.wserp.common.jwt.CurrentUserData;
import com.wserp.userservice.dto.request.UpdateStatus;
import com.wserp.userservice.dto.request.UpdateUserRequest;
import com.wserp.userservice.repository.UserRepository;
import com.wserp.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(currentUserData.getCurrentUserId()), UserDto.class));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateMe(@RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(modelMapper.map(userService.updateUser(currentUserData.getCurrentUserId(), updateUserRequest), UserDto.class));
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMe() {
        userService.deleteUser(currentUserData.getCurrentUserId());
        return ResponseEntity.ok("User deleted successfully");
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<UserDto> updateUserStatus(@PathVariable String id, @RequestBody UpdateStatus updateStatus) {
        return ResponseEntity.ok(modelMapper.map(userService.updateUserStatus(id, updateStatus), UserDto.class));
    }
}
