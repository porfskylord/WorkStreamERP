package com.wserp.authservice.service.intf;

import com.wserp.authservice.dto.LoginRequestDTO;

import java.util.Optional;

public interface AuthService {
    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    boolean validateToken(String substring);
}
