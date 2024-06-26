package com.example.userservice.service;

import com.example.userservice.entity.Admin;
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


    /**
     * 토큰에서 subject 추출
     * @param token 토큰
     * @return subject
     */
    String getSubject(String token);

    /**
     * 관리자 인증 토큰 생성
     * @param admin 관리자
     * @return 토큰 문자열
     */
    String createAdminToken(Admin admin);

    /**
     * 관리자 토큰에서 subject 추출
     * @param token token
     * @return subject
     */
    String getAdminSubject(String token);
}
