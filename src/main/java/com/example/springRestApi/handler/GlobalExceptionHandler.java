package com.example.springRestApi.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Object> handleGenericException(ResponseStatusException e) {
        log.error(e.getMessage());

        return new ResponseEntity<>(e.getReason(),e.getStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception e) {
        log.error(e.getMessage());

        throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
