package com.example.userservice.service;

import com.example.userservice.security.MemberPrincipal;

/**
 * 인증 토큰 서비스
 */
public interface AuthTokenService {
    /**
     * 로그인 인증 토큰 생성
     * @param principal 인증된 회원 객체
     * @return 토큰 문자열
     */
    String createLoginToken(MemberPrincipal principal);
}
