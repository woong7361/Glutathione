package com.example.orderservice.service;

import com.example.orderservice.entity.Wallet;
import com.example.orderservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    /**
     * 지갑 충전
     * @param amount 충전 양
     * @param memberId 회원 식별자
     */
    public void charge(Long amount, Long memberId) {
        Wallet wallet = walletRepository.findByMemberId(memberId)
                .orElseGet(() -> Wallet.builder()
                        .memberId(memberId)
                        .amount(0L)
                        .build());

        wallet.charge(amount);

        walletRepository.save(wallet);
    }

    /**
     * 지갑 조회
     * @param memberId 회원 식별자
     */
    public Long getWalletAmount(Long memberId) {
        return walletRepository.findByMemberId(memberId)
                .map(wallet -> wallet.getAmount())
                .orElseGet(() -> 0L);
    }
}
