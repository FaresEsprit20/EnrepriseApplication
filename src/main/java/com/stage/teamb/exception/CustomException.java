package com.stage.teamb.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
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
    }


}