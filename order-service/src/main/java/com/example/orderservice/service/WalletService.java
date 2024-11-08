package com.example.orderservice.service;

import com.example.orderservice.dto.order.PaymentConfirmRequest;
import com.example.orderservice.entity.Wallet;
import com.example.orderservice.repository.WalletRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 지갑 관련 서비스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final IamportClient iamportClient;

    /**
     * 지갑 충전
     * @param paymentConfirmRequest 충전 확인 요청
     * @param memberId 회원 식별자
     */
    public void charge(PaymentConfirmRequest paymentConfirmRequest, Long memberId) {
        try {
            Payment response = iamportClient.paymentByImpUid(paymentConfirmRequest.getOrderUid()).getResponse();

            if (!response.getStatus().equals("paid")) {
                throw new IllegalArgumentException("결재 미완료");
            }

            Wallet wallet = walletRepository.findByMemberId(memberId)
                    .orElseGet(() -> Wallet.builder()
                            .memberId(memberId)
                            .amount(0L)
                            .build());

            wallet.charge(response.getAmount().longValue());

            walletRepository.save(wallet);
        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException("결재 서버 에러", e);
        }
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
