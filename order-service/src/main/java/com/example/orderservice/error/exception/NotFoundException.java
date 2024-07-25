package com.example.orderservice.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 데이터가 존재하지 않는 exception class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NotFoundException extends RuntimeException{
    private Long id;

    public NotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
