package com.barber.appointment.Security.JWT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig - Configuring security filter chain");
        http
                .csrf(csrf -> {
                    csrf.disable();
                    System.out.println("SecurityConfig - CSRF disabled");
                })
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());
                    System.out.println("SecurityConfig - CORS configured");
                })
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/api/usuario/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/usuario").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/usuario").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/api/usuario").authenticated()
                            .requestMatchers(HttpMethod.GET, "/api/usuario").authenticated()
                            .anyRequest().authenticated();
                    System.out.println("SecurityConfig - Request authorization rules set");
                })
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        System.out.println("SecurityConfig - Security filter chain configuration completed");
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        System.out.println("SecurityConfig - Configuring CORS");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        System.out.println("SecurityConfig - CORS configuration completed");
        return source;
    }
}