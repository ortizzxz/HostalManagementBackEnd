package com.hostalmanagement.app.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Automatically generates a key for HS512
    private final long EXPIRATION_TIME = 6 * 60 * 60 * 1000; // 6 hours

    public String generateToken(String email, Long tenantId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        // Build the JWT token with the user's email and tenantId (optional)
        return Jwts.builder()
                .setSubject(email) // User's email is the subject
                .claim("tenantId", tenantId) // Add tenantId as a claim (optional)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key) // Use the generated key
                .compact(); // Generate and return the token
    }

    // This method can be used to parse and validate the token
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e); // Or return 401
        }
    }

}
