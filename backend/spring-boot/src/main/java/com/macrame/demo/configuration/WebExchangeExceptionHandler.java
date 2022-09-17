package com.macrame.demo.configuration;

import com.macrame.demo.exception.AuthorizationException;
import com.macrame.demo.exception.FileNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice //AOP
public class WebExchangeExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handle(AuthorizationException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handle(FileNotFoundException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

}