package com.example.userservice.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
public class ErrorResponse implements Serializable {
    private String message;
}
