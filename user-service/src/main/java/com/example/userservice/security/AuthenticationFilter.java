package com.example.userservice.security;

import com.example.userservice.entity.Member;
import com.example.userservice.service.AuthTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 회원 로그인 인증 필터
 */
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String TOKEN_RESPONSE_HEADER_NAME = "token";
    private final AuthTokenService tokenService;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthTokenService tokenService)
    {
        super(authenticationManager);
        this.tokenService = tokenService;
    }

    /**
     * 로그인 시도
     * @param request http request
     * @param response http response
     * @return 성공시 인증된 회원 객체를 AuthenticationManger에 저장한다.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Member member = new ObjectMapper().readValue(request.getInputStream(), Member.class);
            log.info("{} is login ing...", member.getLoginId());

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(member.getLoginId(), member.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION");
        }
    }

    /**
     * 로그인 성공시 전략 <br/>
     * 로그인 성공시 header에 {token : [jwt token]} 을 추가한다.
     * @param request http request
     * @param response http response
     * @param authResult auth result
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MemberPrincipal principal = (MemberPrincipal) authResult.getPrincipal();
        log.info("memberId: {} is login successful", principal.getMemberId());

        response.setStatus(200);
        response.addHeader(TOKEN_RESPONSE_HEADER_NAME, tokenService.createLoginToken(principal));
    }
}
