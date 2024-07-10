package com.example.productservice.controller;

import com.example.productservice.dto.inquire.InquireListResponseDto;
import com.example.productservice.resolvehandler.AuthenticationPrincipal;
import com.example.productservice.resolvehandler.Principal;
import com.example.productservice.service.InquireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 문의 관련 엔드포인트
 */
@RestController
@RequiredArgsConstructor
public class InquireController {
    private final InquireService inquireService;

    /**
     * 제품 문의 작성
     * @param productId 제품 식별자
     * @param principal 작성 요청한 회원
     * @param content 문의 작성 요청
     * @return 200 ok
     */
    @PostMapping("/products/{productId}/inquires")
    public ResponseEntity<Object> createProductInquire(
            @PathVariable Long productId, @AuthenticationPrincipal Principal principal,
            @RequestBody Map<String, String> content
        ) {
        inquireService.createProductInquire(productId, principal.getMemberId(), content.get("content"));

        return ResponseEntity.ok().build();
    }

    /**
     * 제품 문의 삭제
     * @param inquireId 제품 식별자
     * @param principal 작성 요청한 회원
     * @return 200 ok
     */
    @DeleteMapping("/products/inquires/{inquireId}")
    public ResponseEntity<Object> deleteProductInquire(
            @PathVariable Long inquireId, @AuthenticationPrincipal Principal principal) {
        inquireService.deleteProductInquire(inquireId, principal.getMemberId());

        return ResponseEntity.ok().build();
    }

    /**
     * 제품 문의 답변 작성
     * @param inquireId 문의 식별자
     * @param content 문의 작성 요청
     * @return 200 ok
     */
    @PostMapping("/products/inquires/{inquireId}/answers")
    public ResponseEntity<Object> createProductInquireAnswer(
            @PathVariable Long inquireId,
            @RequestBody Map<String, String> content
    ) {
        inquireService.createProductInquireAnswer(inquireId, content.get("content"));

        return ResponseEntity.ok().build();
    }

    /**
     * 제품 문의 답변 삭제
     * @param answerId 문의 식별자
     * @return 200 ok
     */
    @DeleteMapping("/products/inquires/answers/{answerId}")
    public ResponseEntity<Object> deleteProductInquireAnswer(@PathVariable Long answerId) {
        inquireService.deleteProductInquireAnswer(answerId);

        return ResponseEntity.ok().build();
    }

    /**
     * 제품 문의 조회
     *
     * @param productId 제품 식별자
     * @return 200 ok
     */
    @GetMapping("/products/{productId}/inquires")
    public ResponseEntity<InquireListResponseDto> getInquires(@PathVariable Long productId) {
        InquireListResponseDto inquires = inquireService.getInquires(productId);

        return ResponseEntity.ok(inquires);
    }
}
