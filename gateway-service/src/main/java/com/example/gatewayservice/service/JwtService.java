package com.example.gatewayservice.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final Environment env;
    public void verify(String jwt, String propertyKey) {
        SecretKey signingKey = getSecretKey(propertyKey);

        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(signingKey)
                .build();

        jwtParser.parseClaimsJws(jwt).getBody().getSubject();
    }

    private SecretKey getSecretKey(String propertyKey) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty(propertyKey).getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        return signingKey;
    }
}
