package com.wserp.authservice.utils;

import com.wserp.authservice.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private final CustomUserDetailsService customUserDetailsService;

    private final SecretKeySpec SECRET_KEY;
    private final long EXPIRATION_TIME;


    public JwtUtils(@Value("${jwt.secret}") String secretString,
                    @Value("${jwt.expiration}") long expiration,
                    CustomUserDetailsService customUserDetailsService) {

        this.SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(secretString), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.EXPIRATION_TIME = expiration;
        this.customUserDetailsService = customUserDetailsService;

    }

    public String generateToken(String username) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.user().getId());
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        claims.put("email", userDetails.user().getEmail());
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuer("WorkStreamERP")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

}

