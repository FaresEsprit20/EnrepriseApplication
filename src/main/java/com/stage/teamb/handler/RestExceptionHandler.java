package com.stage.teamb.handler;

import com.stage.teamb.dtos.errors.ErrorResponseDTO;
import com.stage.teamb.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.security.SignatureException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        // Create a custom error response with the appropriate status code and error messages
        HttpStatus status = HttpStatus.valueOf(ex.getStatus_code());
        ErrorResponseDTO response = new ErrorResponseDTO(status, ex.getMessage(), ex.getErrors());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");
            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
            return errorDetail;
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
            return errorDetail;
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
            return errorDetail;
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
            return errorDetail;
        }

        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
        errorDetail.setProperty("description", "Unknown internal server error.");
        return errorDetail;

    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ProblemDetail> handleBadCredentialsException(ExpiredJwtException exception, WebRequest request) {
//        ProblemDetail errorDetail = null;
//        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
//        errorDetail.setProperty("description", "The username or password is incorrect");
//        return new ResponseEntity<>(errorDetail,HttpStatus.UNAUTHORIZED);
//    }
//
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ProblemDetail> handleExpiredJwtException(ExpiredJwtException exception, WebRequest request) {
//        ProblemDetail errorDetail = null;
//        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//        errorDetail.setProperty("description", "The JWT token has expired");
//        return new ResponseEntity<>(errorDetail,HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(AccountStatusException.class)
//    public ResponseEntity<ProblemDetail> handleAccountStatusException(ExpiredJwtException exception, WebRequest request) {
//        ProblemDetail errorDetail = null;
//        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//        errorDetail.setProperty("description", "The account is locked");
//        return new ResponseEntity<>(errorDetail,HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ProblemDetail> handleAccessDeniedException(ExpiredJwtException exception, WebRequest request) {
//        ProblemDetail errorDetail = null;
//        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//        errorDetail.setProperty("description", "You are not authorized to access this resource");
//        return new ResponseEntity<>(errorDetail,HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(SignatureException.class)
//    public ResponseEntity<ProblemDetail> handleSignatureExceptionException(ExpiredJwtException exception, WebRequest request) {
//        ProblemDetail errorDetail = null;
//        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
//        errorDetail.setProperty("description", "The JWT signature is invalid");
//        return new ResponseEntity<>(errorDetail,HttpStatus.FORBIDDEN);
//    }


}