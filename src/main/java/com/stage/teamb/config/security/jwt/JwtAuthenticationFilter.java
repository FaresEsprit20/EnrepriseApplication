package com.stage.teamb.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    String jwt = null;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("do Filter Internal .....");
        log.info("Request Path: {}", request.getServletPath());
        log.info("Request Method: {}", request.getMethod());

        // Exclude authentication path
        if (request.getServletPath().contains("/api/v1/auth")) {
            log.info("Skipping filter for authentication path");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token from Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.warn("taking bearer token from authorization");
            jwt = authorizationHeader.substring(7); // Extract the token without "Bearer "
        } else {
            // Extract token from cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    log.info("Cookie Name: {}", cookie.getName());
                    log.info("Cookie Value: {}", cookie.getValue());

                    if ("accessToken".equals(cookie.getName()) && !Objects.requireNonNull(jwtService).isTokenExpired(cookie.getValue())) {
                        log.warn("JWT is from cookie");
                        this.jwt = cookie.getValue();
                        break;  // Exit the loop once the accessToken cookie is found and valid
                    } else {
                        if (jwt != null && jwtService != null && jwtService.isTokenExpired(jwt)) {
                            log.warn("JWT in cookie is expired");
                            jwt = null;  // Reset jwt to null so that it doesn't interfere with the Authorization header check
                        }
                    }
                }
            }
        }

        final String userEmail;
        if (jwt == null) {
            log.info("JWT not present, skipping");
            filterChain.doFilter(request, response);
            return;
        }

        // Check if the token has expired
        if (jwtService.isTokenExpired(jwt)) {
            log.warn("JWT has expired");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT has expired");
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
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return;
            }
        } else {
            log.warn("User email is null or Authentication is not null");
        }

        filterChain.doFilter(request, response);
    }

}
