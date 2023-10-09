package com.chs.exception;

import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<?> handleCustomException(AbstractException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(e.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(200)
                .message(" 입력값이 올바르지 않습니다. 필수 항목들을 다 입력해주세요.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(200));
    }
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleUserNotFoundException(AuthenticationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .message(e.getMessage() + " 사용자가 존재하지 않습니다. 올바른 로그인 후 접근 권한이 필요합니다.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(500));
    }

    @ExceptionHandler(RedisConnectionException.class)
    protected ResponseEntity<?> handleRedisConnectionException(RedisConnectionException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(500)
                .message(e.getMessage() + " Redis 서버가 연결되지 않았습니다.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(500));
    }
}
