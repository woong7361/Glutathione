package com.example.userservice.repository;

import com.example.userservice.entity.Admin;
import com.example.userservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 관리자 repository
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * 아이디로 관리자 확인
     * @param loginId 로그인 아이디
     * @return optional admin
     */
    Optional<Admin> findByLoginId(String loginId);
}
