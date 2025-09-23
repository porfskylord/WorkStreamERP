package com.wserp.authservice.service;

import com.wserp.authservice.utils.CustomUserDetails;
import com.wserp.common.dto.AuthUserDto;
import com.wserp.common.proto.AuthUserResponse;
import com.wserp.common.proto.GetUserByUsernameOrEmailRequest;
import com.wserp.common.proto.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public CustomUserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        try {

            GetUserByUsernameOrEmailRequest request = GetUserByUsernameOrEmailRequest.newBuilder()
                    .setUsernameOrEmail(usernameOrEmail)
                    .build();

            AuthUserResponse response = userServiceBlockingStub.getUserByUsernameOrEmail(request);

            if (response == null || response.getId().isEmpty()) {
                throw new UsernameNotFoundException("User not found: " + usernameOrEmail);
            }


            AuthUserDto authUserDto = AuthUserDto.builder()
                    .id(response.getId())
                    .username(response.getUsername())
                    .email(response.getEmail())
                    .password(response.getPassword()) // Make sure this is the hashed password
                    .role(response.getRole())
                    .build();

            return new CustomUserDetails(authUserDto);

        } catch (Exception e) {
            log.error("Error loading user by username/email: " + usernameOrEmail, e);
            throw new UsernameNotFoundException("User not found: " + usernameOrEmail, e);
        }
    }
}