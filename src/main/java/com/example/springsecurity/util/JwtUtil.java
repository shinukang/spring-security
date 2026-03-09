package com.example.springsecurity.util;

import com.example.springsecurity.user.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final int expire;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expire}") int expire) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expire = expire;
    }

    public String createToken(AuthUser user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("idx", user.getIdx())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .expiration(Date.from(Instant.now().plusMillis(expire)))                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
