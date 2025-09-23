package com.wserp.userservice.controller;


import com.wserp.common.jwt.CurrentUserData;
import com.wserp.userservice.repository.UserRepository;
import com.wserp.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


//    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<UserDto>> getAll(@RequestParam(required = false) String role, @RequestParam(required = false) String userStatus, @RequestParam(required = false) String search) {
//        return ResponseEntity.ok(userService.getUsers(role, userStatus, search).stream()
//                .map(user -> modelMapper.map(user, UserDto.class)).toList());
//    }
//
//    @GetMapping("/byId/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
//        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
//    }
//
//    @GetMapping("/me")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<UserDto> getMe() {
//        return ResponseEntity.ok(modelMapper.map(userService.getUserById(currentUserData.getCurrentUserId()), UserDto.class));
//    }
//
//    @GetMapping("/byUsername/{username}")
//    public ResponseEntity<AuthUserDto> getUserByUsername(
//            @PathVariable String username,
//            @RequestHeader(value = "X-INTERNAL-TOKEN", required = false) String token) {
//
//        if (!internalToken.equals(token)) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//        }
//
//        AuthUserDto userDto = modelMapper.map(userService.getUserByUsername(username), AuthUserDto.class);
//        return ResponseEntity.ok(userDto);
//    }
//
//    @PutMapping("/update")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateRequest request) {
//        return ResponseEntity.ok(modelMapper.map(userService.updateUser(currentUserData.getCurrentUserId(), request), UserDto.class));
//    }
//
//    @DeleteMapping("/me")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Void> deleteMe() {
//        userService.deleteUser(currentUserData.getCurrentUserId());
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN")
//    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping("/{id}/userStatus")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<UserDto> updateUserStatus(@PathVariable String id, @RequestBody UpdateStatus updateStatus) {
//        return ResponseEntity.ok(modelMapper.map(userService.updateUserStatus(id, updateStatus), UserDto.class));
//    }

}
