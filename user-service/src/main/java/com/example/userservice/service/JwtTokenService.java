package com.example.userservice.service;

import com.example.userservice.environment.JwtEnvironment;
import com.example.userservice.security.MemberPrincipal;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService implements AuthTokenService{
    private final JwtEnvironment jwtEnvironment;

    public String createLoginToken(MemberPrincipal principal) {

        byte[] secretKeyBytes = Base64.getEncoder()
                .encode(jwtEnvironment.getTokenSecret().getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(String.valueOf(principal.getMemberId()))
                .expiration(Date.from(now.plusMillis(Long.parseLong(jwtEnvironment.getTokenExpirationTime()))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();
    }

    public String getSubject(String jwt) {
        SecretKey signingKey = getSecretKey();

        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(signingKey)
                .build();

        return jwtParser.parseClaimsJws(jwt).getBody().getSubject();
    }

    private SecretKey getSecretKey() {
        byte[] secretKeyBytes = Base64.getEncoder().encode(jwtEnvironment.getTokenSecret().getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        return signingKey;
    }

}
