package com.stage.teamb.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException {

    String message;
    int status_code;
    List<String> errors;

    public CustomException(int code, List<String> errorsList) {
        this.status_code = code;
        errors = errorsList;
    }

    public CustomException(String message, int code) {
        this.message = message;
        this.status_code = code;
        errors.add(message);
    }


}