package com.wserp.userservice.controller;


import com.wserp.userservice.dto.AuthUserDto;
import com.wserp.userservice.dto.UserDto;
import com.wserp.userservice.dto.request.RegisterRequest;
import com.wserp.userservice.dto.request.UserUpdateRequest;
import com.wserp.userservice.filter.CustomPrincipal;
import com.wserp.userservice.repository.UserRepository;
import com.wserp.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(modelMapper.map(userService.saveUser(request), UserDto.class));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAll(){
        return ResponseEntity.ok(userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id){
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getMe(){
        CustomPrincipal customPrincipal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(modelMapper.map(userRepository.findById(customPrincipal.getUserId()).get(), UserDto.class));
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(modelMapper.map(userService.getUserByUsername(username), AuthUserDto.class));
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateUser( @RequestBody UserUpdateRequest request){
        CustomPrincipal customPrincipal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(modelMapper.map(userService.updateUser(customPrincipal.getUserId(), request), UserDto.class));
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteMe(){
        CustomPrincipal customPrincipal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.deleteUser(customPrincipal.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
