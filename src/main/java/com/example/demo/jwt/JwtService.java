package com.example.demo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        long MINUTE_EXPIRATION = 60;
        long JWT_EXPIRATION = 1000 * 60 * MINUTE_EXPIRATION;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        String JWT_KEY = "2f4e1f0fdbbb24280231a988954c20390f3065bb1d27b8ca6cccd473e935644bda8420cc2b08e39d086bddfea994f84f6f5dc70c1cfa627baf7a9c3d4da91549";
        byte[] keyBytes = Decoders.BASE64.decode(JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
