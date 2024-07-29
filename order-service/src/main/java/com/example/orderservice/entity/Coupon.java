package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private Long productId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coupon_image_id")
    private CouponImage couponImage;

    private String name;
    private String description;
    private Integer discount;

    private Boolean isPercent;
    private Boolean disabled;

    public void toggle() {
        this.disabled = !this.disabled;
    }
}
