package com.barber.appointment.Security.JWT;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JwtConfig {

    private static String SECRET_KEY;

    @PostConstruct
    public void init() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32];
        random.nextBytes(key);
        SECRET_KEY = Base64.getEncoder().encodeToString(key);
    }

    public static String getSecretKey() {
        return SECRET_KEY;
    }
}
