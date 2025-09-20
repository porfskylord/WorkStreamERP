package com.wserp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wserp.userservice.entity.UserDetails;
import com.wserp.userservice.entity.enums.Role;
import com.wserp.userservice.entity.enums.Status;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String email;
    private Role role;
    private Status status;
    private UserDetails userDetails;
}
