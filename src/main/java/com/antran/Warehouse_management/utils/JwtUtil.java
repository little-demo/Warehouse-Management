package com.antran.Warehouse_management.utils;

import com.antran.Warehouse_management.entity.Role;
import com.antran.Warehouse_management.entity.User;
import com.antran.Warehouse_management.entity.Warehouse;
import com.antran.Warehouse_management.enums.ERole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.signerKey}")
    private String SECRET_KEY;

    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        // Gắn danh sách roles
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName) // Role.name là ERole
                .toList();
        claims.put("roles", roleNames);
        claims.put("userId", user.getId());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + VALID_DURATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Integer extractUserId(String token) {
        return extractClaims(token).get("userId", Integer.class);
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = extractClaims(token);
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();

            return (username.equals(userDetails.getUsername()) && expiration.after(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
