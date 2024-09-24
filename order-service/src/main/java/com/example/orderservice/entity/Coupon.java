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

    private Long quantity;

    public void toggle() {
        this.disabled = !this.disabled;
    }

    public void decreaseQuantity() {
        if (this.quantity == null || this.quantity <= 0) {
            throw new RuntimeException("쿠폰 발급 수량 부족");
        }

        this.quantity -= 1;
    }
}
