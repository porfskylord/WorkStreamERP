package com.wserp.userservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;


import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

@Component
public class JwtUtil {

    private final SecretKeySpec SECRET_KEY;
    private final long EXPIRATION_TIME;



    public JwtUtil(@Value("${jwt.secret}") String secretString,
                    @Value("${jwt.expiration}") long expiration ){
        this.SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(secretString), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.EXPIRATION_TIME = expiration;

    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
