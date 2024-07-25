package com.example.orderservice.controller;

import com.example.orderservice.dto.coupon.CouponCreateRequest;
import com.example.orderservice.dto.coupon.CouponResponseDto;
import com.example.orderservice.entity.Coupon;
import com.example.orderservice.entity.MemberCoupon;
import com.example.orderservice.resolvehandler.AuthenticationPrincipal;
import com.example.orderservice.resolvehandler.Principal;
import com.example.orderservice.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 쿠폰 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class CouponController {

    public static final String COUPON_RESPONSE_KEY = "coupons";
    private final CouponService couponService;

    /**
     * 관리자의 쿠폰 등록
     *
     * @return 200 ok
     */
    @PostMapping("/coupon")
    public ResponseEntity<Object> createCoupon(@ModelAttribute CouponCreateRequest couponCreateRequest,
                                               @RequestParam MultipartFile multipartFile) {
        couponService.createCoupon(couponCreateRequest, multipartFile);

        return ResponseEntity.ok().build();
    }

    /**
     * 쿠폰 조회
     *
     * @return 쿠폰 리스트
     */
    @GetMapping("/coupon")
    public ResponseEntity<Map<String, List<Coupon>>> getCoupon() {
        List<Coupon> coupons = couponService.getCoupon();

        return ResponseEntity.ok(Map.of(COUPON_RESPONSE_KEY, coupons));
    }

    /**
     * 쿠폰 발급
     *
     * @param principal 발급받는 사용지
     * @param couponId  쿠폰 아이디
     * @return 200 ok
     */
    @PostMapping("/members/coupon/{couponId}")
    public ResponseEntity<Object> issueCoupon(@AuthenticationPrincipal Principal principal, @PathVariable Long couponId) {
        couponService.issue(couponId, principal.getMemberId());

        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 쿠폰 조회
     *
     * @param principal 회원
     * @return 200 ok
     */
    @GetMapping("/members/coupon")
    public ResponseEntity<Map<String, List<CouponResponseDto>>> getMemberCoupon(@AuthenticationPrincipal Principal principal) {
        List<CouponResponseDto> memberCoupons = couponService.getMemberCoupon(principal.getMemberId());

        return ResponseEntity.ok(Map.of(COUPON_RESPONSE_KEY, memberCoupons));
    }
}
