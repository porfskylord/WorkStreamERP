package com.wserp.authservice.client;

import com.wserp.authservice.dto.UserDto;
import com.wserp.authservice.dto.request.RegisterDto;
import com.wserp.authservice.dto.request.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @PostMapping("/users/save")
    ResponseEntity<RegisterDto> saveUser(@RequestBody RegisterRequest request);

    @GetMapping("/users/byUsername/{username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable String username);
}
