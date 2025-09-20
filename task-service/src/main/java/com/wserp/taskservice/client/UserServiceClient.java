package com.wserp.projectservice.client;

import com.wserp.projectservice.dto.MemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8082")
public interface UserServiceClient {

    @GetMapping("/users/byId/{userId}")
    ResponseEntity<MemberDto> getUserById(@PathVariable("userId") String userId);
}
