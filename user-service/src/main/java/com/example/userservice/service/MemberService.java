package com.example.userservice.service;

import com.example.userservice.entity.Member;
import com.example.userservice.error.exception.NotFoundException;
import com.example.userservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원 생성
     * @param member 회원 생성 요청 정보
     */
    public void createMember(Member member) {
        member.encodePassword(passwordEncoder);

        memberRepository.save(member);
        log.info("{} member save successful", member.getMemberName());
    }

    /**
     * login Id 중복 확인
     * @param loginId target login Id
     * @return true/false
     */
    public Boolean isDuplicateLoginId(String loginId) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);

        return member.isPresent();
    }

    /**
     * 회원 정보 업데이트
     * @param memberId 회원 식별자
     * @param updateMember 업데이트 요청 파라미터
     */
    public void updateMember(Long memberId, Member updateMember) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("not exist member", memberId));

        findMember.update(updateMember);
    }
}
