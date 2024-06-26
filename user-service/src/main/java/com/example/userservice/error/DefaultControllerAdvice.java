package com.example.userservice.error;

import com.example.userservice.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(MethodArgumentNotValidException exception) {
        for (FieldError fieldError : exception.getFieldErrors()) {
            log.info("field error - field: {}, message: {}",  fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.info("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(ConstraintViolationException exception) {
        log.info(exception.getMessage());

        log.info("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(IllegalArgumentException exception) {
        log.info(exception.getMessage());

        log.info("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    /**
     * 잘못된 데이터가 바인딩 되었을때 발생하는 에러
     * SQL 문이 잘못되었거나 Data 가 잘못되었을경우
     *
     * @param exception 발생한 에러
     * @return 400 bad request
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(DataIntegrityViolationException exception) {
        log.error("error message: ", exception.getMessage());
        log.error("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException exception) {
        log.info("{} and PK: {}", exception.getMessage(), exception.getPk());

        log.info("{}", exception);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage() + " PK: " + exception.getPk())
                        .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> defualtExceptionHandler(RuntimeException exception) {
        log.info("{}", exception.getMessage());

        log.info("{}", exception);

        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

}
