package com.wserp.apigateway.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final SecretKeySpec SECRET_KEY;

    public JwtUtil(@Value("${jwt.secret}") String secretString) {

        this.SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(secretString), Jwts.SIG.HS256.key().build().getAlgorithm());

    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            return !isTokenExpired(token);

        }catch (SignatureException e) {
            throw new JwtException("Invalid signature");
        }
        catch (JwtException e) {
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

}
