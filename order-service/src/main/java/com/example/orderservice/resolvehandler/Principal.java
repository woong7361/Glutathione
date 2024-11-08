package com.example.orderservice.resolvehandler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 인증 회원 class
 */
@Data
@AllArgsConstructor
public class Principal {
    private Long memberId;
}
