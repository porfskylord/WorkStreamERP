package com.wserp.projectservice.filter;

import com.wserp.projectservice.utills.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");

            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                Claims claims = jwtUtil.getClaims(token.substring(7));

                CustomPrincipal customPrincipal = new CustomPrincipal(claims.get("userId").toString(), claims.getSubject(),claims.get("email").toString());

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(claims.get("role").toString());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customPrincipal, null, Collections.singleton(authority));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            log.error("User is not authenticated",e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}