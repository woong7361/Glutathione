package com.example.userservice.repository;

import com.example.userservice.entity.DeletedMember;
import com.example.userservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 삭제된 회원 repository
 */
public interface DeletedMemberRepository extends JpaRepository<DeletedMember, Long> {
    Optional<DeletedMember> findByLoginId(String loginId);
}
