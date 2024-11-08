package com.example.orderservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품 수량 감소 요청 DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReduceQuantityRequestDto {
    Long productId;
    Long quantity;
}
