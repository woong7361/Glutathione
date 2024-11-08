package com.example.orderservice.repository;

import com.example.orderservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 개인 지갑 repository
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    /**
     * 회원의 지갑 확인
     * @param memberId 회원 식별자
     * @return 회원 지갑
     */
    Optional<Wallet> findByMemberId(Long memberId);
}
