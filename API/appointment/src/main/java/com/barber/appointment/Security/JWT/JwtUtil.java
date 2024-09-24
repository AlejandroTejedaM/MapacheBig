package com.barber.appointment.Security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    //openssl rand -base64 32
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, JwtConfig.getSecretKey())
                .compact();
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public static boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public static boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
