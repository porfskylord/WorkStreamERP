package com.wserp.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wserp.userservice.entity.UserDetails;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String email;
    private UserDetails userDetails;
}
