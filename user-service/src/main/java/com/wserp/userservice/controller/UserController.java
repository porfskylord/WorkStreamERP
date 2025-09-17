package com.wserp.userservice.controller;


import com.wserp.userservice.dto.AuthUserDto;
import com.wserp.userservice.dto.UserDto;
import com.wserp.userservice.dto.request.RegisterRequest;
import com.wserp.userservice.dto.request.UserUpdateRequest;
import com.wserp.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Management")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(modelMapper.map(userService.saveUser(request), UserDto.class));
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> test(){
        log.info("Test endpoint accessed by admin");
        return ResponseEntity.ok("Test - Admin Access Granted");
    }

    @GetMapping("/test1")
    public ResponseEntity<String> test1(){
        log.info("Test");return ResponseEntity.ok("Test");
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAll(){
        log.info("Get all users - Admin access");
        return ResponseEntity.ok(userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id){
        log.info("Get user by id: {}", id);
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

    @GetMapping("/getUserByUsername/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsername(@PathVariable String username){
        log.info("Get user by username: {}", username);
        return ResponseEntity.ok(modelMapper.map(userService.getUserByUsername(username), AuthUserDto.class));
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(modelMapper.map(userService.getUserByEmail(email), UserDto.class));
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userService.getUserById(#id).username == principal")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(modelMapper.map(userService.updateUser(id, request), UserDto.class));
    }

    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userService.getUserById(#id).username == principal")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
