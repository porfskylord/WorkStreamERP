package com.wserp.userservice.dto.request;

import com.wserp.userservice.entity.UserDetails;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String password;
    private String email;
    private UserDetails userDetails;
}
