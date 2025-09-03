package com.smartexpense.gateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class TokenGenerator {
    // run as a normal Java application in IntelliJ to print a token
    public static void main(String[] args) {
        // same secret as application.yml: replace with your dev secret
        String secret = "dev-very-strong-secret-that-should-be-rotated";
        byte[] key = secret.getBytes();
        String jws = Jwts.builder()
                .setSubject("c7a3b1e8-ff4d-4d7a-8d3f-4a6b2c7e1b3a") // sample userId sub
                .setId(UUID.randomUUID().toString())
                .setIssuer("dev-auth")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .claim("email", "user@example.com")
                .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS256)
                .compact();
        System.out.println("SAMPLE JWT (HS256):");
        System.out.println(jws);
    }
}

