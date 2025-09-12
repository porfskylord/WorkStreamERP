package com.wserp.userservice.entity.mappers;

import com.wserp.commondto.AuthUserDto;
import com.wserp.commondto.UserResponseDto;
import com.wserp.userservice.entity.Users;

public class UserMapper {

    public static UserResponseDto toUserDto(Users user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName().toString()
        );
    }

    public static AuthUserDto toAuthUserDto(Users user) {
        return new AuthUserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getName().toString());

    }
}
