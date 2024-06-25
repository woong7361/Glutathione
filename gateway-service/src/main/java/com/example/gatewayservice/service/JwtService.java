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
    public Boolean verify(String jwt) {
        SecretKey signingKey = getSecretKey();

        String subject;
        try {
            JwtParser jwtParser = Jwts.parser()
                    .setSigningKey(signingKey)
                    .build();

            subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception ex) {
            log.error("authentication Error : {}", ex.getMessage());
            return false;
        }

        if (subject == null || subject.isEmpty()) {
            log.error("authentication Error memberId is: {}", subject);
            return false;
        }

        return true;
    }

    private SecretKey getSecretKey() {
        byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty("token.secret").getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        return signingKey;
    }
}
