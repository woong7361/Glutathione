package com.example.productservice.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 에러에 대한 응답 포맷
 */
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    public String message;
}
