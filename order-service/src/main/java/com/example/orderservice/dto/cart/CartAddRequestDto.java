package com.example.orderservice.dto.cart;

import lombok.*;

/**
 * 장바구니 추가 요청 dto
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartAddRequestDto {

    private Long productId;
    private Integer quantity;
}
