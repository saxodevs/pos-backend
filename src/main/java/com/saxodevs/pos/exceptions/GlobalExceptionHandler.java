package com.saxodevs.pos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<?> handleAppException(AppException ex) {
    return ResponseEntity
            .status(ex.getStatus())
            .body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneral(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("message", "Something went wrong"));
  }
}
