package com.example.orderservice.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 오류 응답 메시지 포맷
 */
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    public String message;
}
