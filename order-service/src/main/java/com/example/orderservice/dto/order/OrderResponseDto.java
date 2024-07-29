package com.example.orderservice.dto.order;

import com.example.orderservice.dto.coupon.CouponResponseDto;
import com.example.orderservice.dto.product.ProductDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private String senderName;
    private String senderPhoneNumber;
    private String senderEmail;

    private String receiverName;
    private String receiverPhoneNumber;

    private String address;
    private String addressDetail;
    private List<OrderProductsDto> orderProducts;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductsDto {
        private ProductDetailResponseDto productDetailResponseDto;
        private Long quantity;
        private CouponResponseDto couponResponseDto;
    }

}
