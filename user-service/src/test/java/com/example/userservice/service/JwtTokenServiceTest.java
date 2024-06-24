package com.example.userservice.service;

import com.example.userservice.entity.Member;
import com.example.userservice.environment.JwtEnvironment;
import com.example.userservice.security.MemberPrincipal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JwtTokenServiceTest {
    private JwtEnvironment jwtEnvironment = Mockito.mock(JwtEnvironment.class);
    private JwtTokenService jwtTokenService = new JwtTokenService(jwtEnvironment);

    @Nested
    @DisplayName("JWT 토큰 생성 테스트")
    public class CreateTokenTest {
        @DisplayName("정상 생성")
        @Test
        public void success() throws Exception {
            //given
            String secretKey = "secret123456789123456789123456789123456789";

            Mockito.when(jwtEnvironment.getTokenSecret()).thenReturn(secretKey);
            Mockito.when(jwtEnvironment.getTokenExpirationTime()).thenReturn("10000");

            MemberPrincipal principal = new MemberPrincipal(Member.builder()
                    .memberId(1L)
                    .loginId("id")
                    .password("password")
                    .build());

            //when
            //then
            jwtTokenService.createLoginToken(principal);
        }
    }
}