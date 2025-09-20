package com.wserp.authservice.service;

import com.wserp.authservice.client.UserServiceClient;
import com.wserp.authservice.dto.AuthUserDto;
import com.wserp.authservice.exception.UserNotFoundException;
import com.wserp.authservice.utils.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserServiceClient userServiceClient;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AuthUserDto userDto = userServiceClient.getUserByUsername(username).getBody();
            if (userDto == null) {
                throw new UserNotFoundException("User not found with username: " + username);
            }
            return new CustomUserDetails(userDto);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error loading user: " + username, e);
        }
    }
}