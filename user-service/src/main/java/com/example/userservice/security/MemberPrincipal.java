package com.example.userservice.security;

import com.example.userservice.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * security의 인증된 회원 객체
 */
@AllArgsConstructor
public class MemberPrincipal implements UserDetails {
    private Member member;

    /**
     * @return 회원 비밀번호 반환
     */
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    /**
     * @return 회원 loginId 반환
     */
    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    /**
     * @return 회원 식별자 반환
     */
    public Long getMemberId() {
        return member.getMemberId();
    }

    /**
     * @return 회원 권한 반환
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    /**
     * @return 회원이 만료가 아닌지 반환
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return 회원이 잠겨있지 않은지 반환
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return 회원의 인증이 만료되어있지 않은지 반환
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return 회원이 활성상태인지 반환
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
