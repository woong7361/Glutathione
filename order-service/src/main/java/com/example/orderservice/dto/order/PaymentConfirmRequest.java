package com.example.orderservice.dto.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결재 확인 요청 DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfirmRequest {
    private String orderUid;

}
