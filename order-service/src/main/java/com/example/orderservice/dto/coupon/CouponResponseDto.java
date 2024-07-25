package com.example.orderservice.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponseDto {
    private Long couponId;

    private String name;
    private String description;
    private Integer discount;
    private Boolean isUsed;

    private Boolean isPercent;

    private Long couponImageId;
    private Long productId;
}
