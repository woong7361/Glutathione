package com.example.userservice.security;

import com.example.userservice.entity.Member;
import com.example.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * spring security에서 사용되는 UserService
 */
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * 주어진 회원 이름으로 회원이 존재하는지 확인한다.
     * @param username 회원 아이디
     * @return 인증이 필요한 회원
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));

        if (member.getIsDelete()) {
            return new MemberPrincipal(member, false);
        }
        return new MemberPrincipal(member);
    }
}
