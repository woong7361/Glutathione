package com.example.orderservice.controller;

import com.example.orderservice.dto.order.PaymentConfirmRequest;
import com.example.orderservice.dto.wallet.ChargeRequestDto;
import com.example.orderservice.resolvehandler.AuthenticationPrincipal;
import com.example.orderservice.resolvehandler.Principal;
import com.example.orderservice.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 지갑(페이) 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class WalletController {
    public static final String AMOUNT_RESPONSE_KEY = "amount";
    private final WalletService walletService;

    /**
     * 지갑 충전
     * @param principal 회원 객체
     * @param paymentConfirmRequest 충전 확인 요청
     * @return 200 ok
     */
    @PostMapping("/wallet")
    public ResponseEntity<Object> chargeWallet(
            @AuthenticationPrincipal Principal principal, @Valid @RequestBody PaymentConfirmRequest paymentConfirmRequest) {
        walletService.charge(paymentConfirmRequest, principal.getMemberId());

        return ResponseEntity.ok().build();
    }

    /**
     * 지갑 조회
     * @param principal 회원 객체
     * @return 지갑 충전양
     */
    @GetMapping("/wallet")
    public ResponseEntity<Map<String, Long>> getWallet(@AuthenticationPrincipal Principal principal) {
        Long walletAmount = walletService.getWalletAmount(principal.getMemberId());

        return ResponseEntity
                .ok(Map.of(AMOUNT_RESPONSE_KEY, walletAmount));
    }
}
