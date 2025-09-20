package com.wserp.authservice.client;

import com.wserp.authservice.config.UserServiceFeignConfig;
import com.wserp.authservice.dto.AuthUserDto;
import com.wserp.authservice.dto.request.RegisterDto;
import com.wserp.authservice.dto.request.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE",
        configuration = UserServiceFeignConfig.class)
public interface UserServiceClient {

    @PostMapping("/users/save")
    ResponseEntity<RegisterDto> saveUser(@RequestBody RegisterRequest request);

    @GetMapping("/users/byUsername/{username}")
    ResponseEntity<AuthUserDto> getUserByUsername(@PathVariable String username);
}
