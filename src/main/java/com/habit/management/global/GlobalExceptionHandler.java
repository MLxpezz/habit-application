package com.habit.management.global;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse
                                .<Void>builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityExists(EntityExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                  ApiResponse
                          .<Void>builder()
                          .status(HttpStatus.CONFLICT.value())
                          .message(exception.getMessage())
                          .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException exception) {
        String message = exception
                .getBindingResult()
                .getFieldErrors()
                .stream().map(FieldError::getField)
                .collect(Collectors.joining(","));


        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse
                                .<Void>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(message)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.<Void>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Error del servidor")
                        .build());
    }


}
