package com.stage.teamb.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.teamb.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Collections;

@Slf4j
@Data
public class TokenExceptionHandler {


    public static void handleMissingToken(HttpServletResponse response) throws IOException {
        log.warn("No valid token found in cookie or authorization header");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProblemDetail errorDetail;
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), "No valid token found in cookie or authorization header");
            errorDetail.setProperty("description", "Unknown internal server error");
            JsonNode jsonNode = objectMapper.createObjectNode()
                    .put("type", errorDetail.getType().toString())
                    .put("title", errorDetail.getTitle())
                    .put("status", errorDetail.getStatus())
                    .put("detail",errorDetail.getDetail())
                    .put("description", errorDetail.getProperties().get("description").toString());
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            response.setStatus(errorDetail.getStatus());
            response.setContentType("application/json");
            response.getWriter().write(jsonString);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception exception) {
            response.getWriter().write("Non Valid JWT " + exception.getMessage());
            response.getWriter().flush();
            response.getWriter().close();
            throw new CustomException(500, Collections.singletonList(exception.getMessage()));
        }
    }



    public static void handleExceptionToProblemDetail(Exception exception, HttpServletResponse response) throws IOException {
        ProblemDetail errorDetail;
        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), "JWT token has expired");
            errorDetail.setProperty("description", "The JWT token has expired");
        } else if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");
        } else if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        } else if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        } else if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        } else {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), "Internal server error");
            errorDetail.setProperty("description", "Unknown internal server error");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.createObjectNode()
                    .put("type", errorDetail.getType().toString())
                    .put("title", errorDetail.getTitle())
                    .put("status", errorDetail.getStatus())
                    .put("detail",errorDetail.getDetail())
                    .put("description", errorDetail.getProperties().get("description").toString());
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            response.setStatus(errorDetail.getStatus());
            response.setContentType("application/json");
            response.getWriter().write(jsonString);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            response.getWriter().write("Non Valid JWT " + exception.getMessage());
            response.getWriter().flush();
            response.getWriter().close();
            throw new CustomException(500, Collections.singletonList(e.getMessage()));
        }
    }


    //    private String extractTokenFromCookies(Cookie[] cookies) {
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("accessToken".equals(cookie.getName()) && !jwtService.isTokenExpired(cookie.getValue())
//                        && !jwtService.isExpiredCookie(cookie.getValue())) {
//                    log.info("JWT is from cookie");
//                    return cookie.getValue();
//                } else {
//                    log.debug("Ignoring cookie: {} with value: {}", cookie.getName(), cookie.getValue());
//                }
//            }
//        }
//        return null;
//    }


}
