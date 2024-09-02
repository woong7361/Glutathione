package com.example.productservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Setter
    private Long memberId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductDto {
        private Long productId;
        private Long quantity;
        private Long memberCouponId;
    }

}
