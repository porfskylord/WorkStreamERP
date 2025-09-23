package com.wserp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wserp.common.enums.Role;
import com.wserp.userservice.entity.enums.UserStatus;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String email;
    private Role role;
    private UserStatus userStatus;
}
