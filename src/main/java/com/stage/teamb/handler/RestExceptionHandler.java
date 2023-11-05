package com.stage.teamb.handler;

import com.stage.teamb.dtos.errors.ErrorResponseDTO;
import com.stage.teamb.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        // Create a custom error response with the appropriate status code and error messages
        HttpStatus status = HttpStatus.valueOf(ex.getStatus_code());
        ErrorResponseDTO response = new ErrorResponseDTO(status, ex.getMessage(), ex.getErrors());
        return new ResponseEntity<>(response, status);
    }
}