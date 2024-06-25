package com.example.userservice.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private Long pk;

    public NotFoundException(String message, Long pk) {
        super(message);
        this.pk = pk;
    }
}
