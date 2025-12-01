package com.taskmanagement.task_management_api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleNotFound(EmployeeNotFoundException ex ){
        ErrorResponse errorResponse= ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description("Employee Not Found")
                .build();
        return new  ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleNotFound(TaskNotFoundException ex ){
        ErrorResponse errorResponse= ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description("Task Not Found")
                .build();
        return new  ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<ErrorResponse> HandleUnauthorized(AuthenticationFailed ex){
        ErrorResponse errorResponse= ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description("Unauthorized")
                .build();
        return new  ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

}
