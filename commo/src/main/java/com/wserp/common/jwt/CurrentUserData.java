package com.wserp.common.jwt;


import com.wserp.common.models.CustomPrincipal;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserData {
    public CustomPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomPrincipal)) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user found");
        }
        return (CustomPrincipal) authentication.getPrincipal();
    }

    public String getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }

    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    public String getCurrentUserRole() {
        return getCurrentUser().getRole();
    }
}
