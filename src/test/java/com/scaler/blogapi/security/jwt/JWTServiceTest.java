package com.scaler.blogapi.security.jwt;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JWTServiceTest {
    private final JWTService jwtService = new JWTService();
    @Test
    public void canCreateJWTFromUserId(){
        var userId = UUID.randomUUID();
        var jwt = jwtService.createJWT(userId);
        UUID returnedUserId = jwtService.getUserIdFromJWT(jwt);
        assertEquals(userId,returnedUserId);
    }
}
