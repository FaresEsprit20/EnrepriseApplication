package com.stage.teamb.dtos.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ErrorResponseDTO {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorResponseDTO(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }


}
