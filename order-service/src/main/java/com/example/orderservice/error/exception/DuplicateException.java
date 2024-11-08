package com.example.orderservice.error.exception;

/**
 * 중복되는 요청 Exception class
 */
public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
    }
}
