package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 발급되는 쿠폰
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCouponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    private Long memberId;

    private Boolean isUsed;

    public void setCouponId(Long couponId) {
        this.coupon = Coupon.builder()
                .couponId(couponId)
                .build();
    }
}
