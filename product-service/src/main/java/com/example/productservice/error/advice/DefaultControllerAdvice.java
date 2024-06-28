package com.example.productservice.error.advice;

import com.example.productservice.error.ErrorResponse;
import com.example.productservice.error.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultControllerAdvice {

    /**
     * validation exception을 잡는 hanlder
     * @param exception @Valid validation error
     * @return 400 bad request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(MethodArgumentNotValidException exception) {
        for (FieldError fieldError : exception.getFieldErrors()) {
            log.debug("field error - field: {}, message: {}",  fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.info("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    /**
     * db 제약조건을 지키지 않았을때 발생하는 exception handler
     * @param exception 제약조건
     * @return 400 bad request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException exception) {
        log.info("database constraint exception: 제약조건을 지켜주세요!");
        log.info("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    /**
     * db에 해당하는 데이터가 없을때 발생하는 exception handler
     * @param exception data not found
     * @return 400 bad request
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException exception) {
        log.info("{} and Id: {}", exception.getMessage(), exception.getId());

        log.info("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage() + " Id: " + exception.getId())
                        .build()
                );
    }


    /**
     * 그외 다른 exception handler
     * @param exception 그 외
     * @return 500 internal server error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception exception) {
        log.info("등록하지 않은 에러입니다. 서버에 알려주세요! {}", exception.getClass());
        log.info("{}", exception);

        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }
}
