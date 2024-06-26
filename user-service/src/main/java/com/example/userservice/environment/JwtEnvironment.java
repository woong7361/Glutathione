package com.example.userservice.environment;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * jwt 환경변수 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtEnvironment {
    private final Environment environment;

    /**
     * jwt 비밀키를 반환한다.
     * @return secretKey
     */
    public String getTokenSecret() {
        return environment.getProperty("token.secret");
    }

    /**
     * jwt token 만료시간을 반환한다.
     * @return 만료시간 (단위 millis)를 반환
     */
    public String getTokenExpirationTime() {
        return environment.getProperty("token.expiration_time");
    }

    /**
     * 관리자 비밀키를 반환한다.
     * @return 비밀키 문자열
     */
    public String getAdminSecret() {
        return environment.getProperty("admin.secret");
    }

    /**
     * 관리자 토큰 만료시간을 반환한다.
     * @return 만료시간 (단위 millis)를 반환
     */
    public String getAdminTokenExpirationTime() {
        return environment.getProperty("admin.expiration_time");
    }
}
