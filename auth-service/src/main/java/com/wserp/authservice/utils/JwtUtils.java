package com.wserp.authservice.utils;

import com.wserp.authservice.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private final CustomUserDetailsService customUserDetailsService;

    private final SecretKeySpec SECRET_KEY;
    private final long EXPIRATION_TIME;
    private final long REFRESH_EXPIRATION_TIME;


    public JwtUtils(@Value("${jwt.secret}") String secretString,
                    @Value("${jwt.expiration}") long expiration,
                    @Value("${jwt.refresh-expiration}") long refreshExpiration,
                    CustomUserDetailsService customUserDetailsService) {

        this.SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(secretString), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.EXPIRATION_TIME = expiration;
        this.REFRESH_EXPIRATION_TIME = refreshExpiration;
        this.customUserDetailsService = customUserDetailsService;

    }

    public String generateAccessToken(String usernameOrEmail) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(usernameOrEmail);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.user().getId());
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        claims.put("email", userDetails.user().getEmail());
        return createToken(claims, userDetails, EXPIRATION_TIME);
    }

    public String generateRefreshToken(String usernameOrEmail) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(usernameOrEmail);
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails, REFRESH_EXPIRATION_TIME);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails, long expirationTime) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuer("WorkStreamERP")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
    }

    public long getAccessTokenExpirationInMillis() {
        return EXPIRATION_TIME;
    }

    public long getRefreshTokenExpirationInMillis() {
        return REFRESH_EXPIRATION_TIME;
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            return !isTokenExpired(token);

        } catch (SignatureException e) {
            throw new JwtException("Invalid signature");
        } catch (JwtException e) {
            throw new JwtException("Invalid token");
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

}

