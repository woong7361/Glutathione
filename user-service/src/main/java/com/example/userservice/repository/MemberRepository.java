package com.example.userservice.repository;

import com.example.userservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 repository
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * loginId로 회원을 검색한다.
     * @param loginId login Id
     * @return optional 회원
     */
    Optional<Member> findByLoginId(String loginId);
}
