package com.example.orderservice.dto.cart;

import com.example.orderservice.dto.coupon.CouponResponseDto;
import com.example.orderservice.dto.product.ProductDetailResponseDto;
import com.example.orderservice.entity.MemberCoupon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CartResponseDto {
    private Long cartId;
    private ProductDetailResponseDto product;

    private Integer quantity;

    private List<CouponResponseDto> availableCoupons;

    public CartResponseDto(Long cartId, Integer quantity, ProductDetailResponseDto product) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.product = product;
    }

    public void setAvailableCoupons(List<MemberCoupon> memberCoupons) {
        this.availableCoupons = memberCoupons
                .stream()
                .map((mc) -> CouponResponseDto.builder()
                        .couponId(mc.getMemberCouponId())
                        .name(mc.getCoupon().getName())
                        .description(mc.getCoupon().getDescription())
                        .discount(mc.getCoupon().getDiscount())
                        .isPercent(mc.getCoupon().getIsPercent())
                        .couponImageId(mc.getCoupon().getCouponImage().getCouponImageId())
                        .productId(mc.getCoupon().getProductId())
                        .build())
                .toList();
    }
}
