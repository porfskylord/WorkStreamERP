package com.wserp.userservice.service.interfaces;

import com.wserp.commondto.AuthUserDto;
import com.wserp.commondto.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(Long id);

    AuthUserDto getUserByEmail(String email);
}
