package com.wserp.authservice.client;

import com.wserp.commondto.AuthUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service", url = "http://localhost:8082/users")
public interface UserServiceClient {
    @GetMapping("/userbyemail/{email}")
    public Optional<AuthUserDto> getUserByEmail(@PathVariable String email);
}
