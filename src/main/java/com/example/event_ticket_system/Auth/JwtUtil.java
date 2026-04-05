package com.example.event_ticket_system.Auth;

import com.example.event_ticket_system.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final SecretKey secretKey;


    public JwtUtil(@Value("${jwt.secret}") String secret) {
        //byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(String email, String role){
        return Jwts.builder().subject(email).claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60 ) )
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }
    public Role extractRole(String token) {
        String curRole =  getClaims(token).get("role", String.class);
        return Role.valueOf(curRole);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration != null && expiration.before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
