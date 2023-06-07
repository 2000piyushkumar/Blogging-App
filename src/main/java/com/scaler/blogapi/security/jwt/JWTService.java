package com.scaler.blogapi.security.jwt;

import com.auth0.jwt.JWT;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.UUID;

@Service
public class JWTService {
    private final Algorithm algorithm = Algorithm.HMAC256("Secret Sign in Key (Should be in env or config)");
    public String createJWT(UUID userId){
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*7))
                .sign(algorithm);
    }
    public UUID getUserIdFromJWT(String jwt){
        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(jwt);
        var subject = decodedJWT.getSubject();
        return UUID.fromString(subject);
    }
}
