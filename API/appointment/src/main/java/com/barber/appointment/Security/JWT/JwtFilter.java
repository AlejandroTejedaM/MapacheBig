package com.barber.appointment.Security.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("JwtFilter - Request URI: " + requestURI + ", Method: " + method);

        // Allow access to specific endpoints without JWT
        if (requestURI.equals("/api/usuario/login") || (requestURI.equals("/api/usuario") && method.equals("POST"))) {
            System.out.println("JwtFilter - Public endpoint accessed: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("JwtFilter - Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("JwtFilter - Token extracted: " + token);
            try {
                Claims claims = JwtUtil.extractAllClaims(token);
                String username = JwtUtil.extractEmail(token);

                if (JwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("JwtFilter - User authenticated: " + username);
                } else {
                    System.out.println("JwtFilter - Token validation failed");
                }
            } catch (Exception e) {
                System.out.println("JwtFilter - Invalid JWT token: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        } else {
            System.out.println("JwtFilter - No JWT token found in request headers");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid");
            return;
        }

        System.out.println("JwtFilter - Proceeding with filter chain");
        filterChain.doFilter(request, response);
    }
}