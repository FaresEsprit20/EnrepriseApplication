package com.stage.teamb.config.security.jwt;

import com.stage.teamb.exception.CustomException;
import com.stage.teamb.handler.TokenExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
//    private final HttpSessionSecurityContextRepository securityContextRepository =
//            new HttpSessionSecurityContextRepository();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("Entering doFilterInternal");
        log.info("Do filter Internal Starts ");
        logRequestInfo(request);

        if (isAuthenticationPath(request)) {
            log.info("Skipping filter for authentication path");
            filterChain.doFilter(request, response);
            return;
        }
        String jwt =  extractTokenFromAuthorizationHeader(request.getHeader("Authorization"));
        if (jwt == null) {
            TokenExceptionHandler.handleMissingToken(response);
            return;
        }
        try {
            processToken(jwt, request, response, filterChain);
        } catch (Exception exception) {
            TokenExceptionHandler.handleExceptionToProblemDetail(exception, response);
        }
        log.debug("Exiting doFilterInternal");
    }

    private void logRequestInfo(HttpServletRequest request) {
        log.info("Request Path: {}", request.getServletPath());
        log.info("Request Method: {}", request.getMethod());
    }

    private boolean isAuthenticationPath(HttpServletRequest request) {
        return request.getServletPath().contains("/api/v1/auth");
    }



private void processToken(String jwt, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    final String userEmail;
    log.debug("Before checking if the token has expired");

    // Check if the token has expired
    if (jwtService.isTokenExpired(jwt)) {
        log.warn("JWT has expired");
        throw new ExpiredJwtException(null, null, "JWT has expired");
    }

    // Extract user email from token
    userEmail = jwtService.extractUsername(jwt);
    log.info("User Email: {}", userEmail);

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        // Load user details from user service
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        // Check if the token is valid
        if (jwtService.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            // Add logs here to check if the control reaches this point
            log.info("Processing token: {}", "*****");
            log.info("Authentication Object: {}", SecurityContextHolder.getContext().getAuthentication().getName());
            filterChain.doFilter(request, response);
            log.info("doFilterInternal success");
        } else {
            throw new CustomException(403, Collections.singletonList("JWT is Not Valid"));
        }
    } else {
        log.warn("User email is null or Authentication is not null");
    }
}

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
//        log.warn("Auth header  "+authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Taking bearer token from authorization");
            return authorizationHeader.substring(7).trim(); // Extract the token without "Bearer "
        }
        return null;
    }


}




