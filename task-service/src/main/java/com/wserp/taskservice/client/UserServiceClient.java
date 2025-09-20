package com.wserp.taskservice.client;

import com.wserp.taskservice.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/users/byId/{userId}")
    ResponseEntity<MemberDto> getUserById(@PathVariable("userId") String userId);
}
