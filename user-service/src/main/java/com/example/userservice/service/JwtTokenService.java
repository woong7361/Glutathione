package com.example.userservice.service;

import com.example.userservice.entity.Admin;
import com.example.userservice.environment.JwtEnvironment;
import com.example.userservice.security.MemberPrincipal;
import io.jsonwebtoken.JwtBuilder;
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

    @Override
    public String createLoginToken(MemberPrincipal principal) {
        JwtBuilder jwtBuilder = tokenBuilder(jwtEnvironment.getTokenSecret(), jwtEnvironment.getTokenExpirationTime());

        return jwtBuilder
                .subject(String.valueOf(principal.getMemberId()))
                .compact();
    }

    @Override
    public String getSubject(String jwt) {
        SecretKey signingKey = getParseSecretKey(jwtEnvironment.getTokenSecret());

        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(signingKey)
                .build();

        return jwtParser.parseClaimsJws(jwt).getBody().getSubject();
    }

    @Override
    public String createAdminToken(Admin admin) {
        JwtBuilder jwtBuilder = tokenBuilder(jwtEnvironment.getAdminSecret(), jwtEnvironment.getAdminTokenExpirationTime());

        return jwtBuilder
                .subject(admin.getAdminId().toString())
                .compact();
    }

    @Override
    public String getAdminSubject(String jwt) {
        SecretKey signingKey = getParseSecretKey(jwtEnvironment.getAdminSecret());

        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(signingKey)
                .build();

        return jwtParser.parseClaimsJws(jwt).getBody().getSubject();
    }


    private JwtBuilder tokenBuilder(String secret, String ExpirationTime) {
        byte[] secretKeyBytes = Base64.getEncoder()
                .encode(secret.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();

        return Jwts.builder()
                .expiration(Date.from(now.plusMillis(Long.parseLong(ExpirationTime))))
                .issuedAt(Date.from(now))
                .signWith(secretKey);
    }

    private SecretKey getParseSecretKey(String secret) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        return signingKey;
    }

}
