package com.example.orderservice.dto.order;

import com.example.orderservice.dto.product.ProductDetailResponseDto;
import com.example.orderservice.entity.MemberCoupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderProcessDto {
    private ProductDetailResponseDto productDetail;
    private MemberCoupon memberCoupon;
    private Long quantity;
}
