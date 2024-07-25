package com.example.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String senderName;
    private String senderPhoneNumber;
    private String senderEmail;

    private String receiverName;
    private String receiverPhoneNumber;

    private String address;
    private String addressDetail;

    private List<OrderProductDto> orderProducts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductDto {
        private Long productId;
        private Long quantity;
        private Long memberCouponId;
    }

}
