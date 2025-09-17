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
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuer(userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

//    public Boolean validateToken(String token) {
//        try {
//            Jwts.parser().verifyWith(SECRET_KEY)
//                    .build()
//                    .parseSignedClaims(token);
//
//            return !isTokenExpired(token);
//
//        }catch (SignatureException e) {
//            throw new JwtException("Invalid signature");
//        }
//        catch (JwtException e) {
//            throw new JwtException("Invalid token");
//        }
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaims(token, Claims::getExpiration);
//    }
//
//
//    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(SECRET_KEY)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaims(token, Claims::getSubject);
//    }
//
//    public long getExpirationTime() {
//        return EXPIRATION_TIME;
//    }
}

