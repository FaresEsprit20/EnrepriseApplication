package com.stage.teamb.config.security.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.teamb.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        logRequestInfo(request);
        if (isAuthenticationPath(request)) {
            log.info("Skipping filter for authentication path");
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = extractToken(request);
        if (jwt == null) {
            handleMissingToken(response);
            return;
        }
        try {
            processToken(jwt, request, response, filterChain);
        } catch (Exception exception) {
            convertExceptionToProblemDetail(exception, response);
        }
    }

    private void logRequestInfo(HttpServletRequest request) {
        log.info("Request Path: {}", request.getServletPath());
        log.info("Request Method: {}", request.getMethod());
    }

    private boolean isAuthenticationPath(HttpServletRequest request) {
        return request.getServletPath().contains("/api/v1/auth");
    }

    private String extractToken(HttpServletRequest request) {
        String jwt = extractTokenFromCookies(request.getCookies());
        if (jwt == null) {
            jwt = extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        }
        return jwt;
    }

    private void handleMissingToken(HttpServletResponse response) throws IOException {
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

    private void processToken(String jwt, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final String userEmail;
        // Check if the token has expired
        if (jwtService.isTokenExpired(jwt)) {
            log.warn("JWT has expired");
            throw new ExpiredJwtException(null, null, "JWT has expired");
        }
        // Extract user email from token
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from user service
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("do Filter Internal Success");
            } else {
                throw new CustomException(403, Collections.singletonList("JWT is Not Valid"));
            }
        } else {
            log.warn("User email is null or Authentication is not null");
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName()) && !jwtService.isTokenExpired(cookie.getValue())
                        && !jwtService.isExpiredCookie()) {
                    log.info("JWT is from cookie");
                    return cookie.getValue();
                } else {
                    log.debug("Ignoring cookie: {} with value: {}", cookie.getName(), cookie.getValue());
                }
            }
        }
        return null;
    }

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Taking bearer token from authorization");
            return authorizationHeader.substring(7).trim(); // Extract the token without "Bearer "
        }
        return null;
    }
    private void convertExceptionToProblemDetail(Exception exception, HttpServletResponse response) throws IOException {
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


}




