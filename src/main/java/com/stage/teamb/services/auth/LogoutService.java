package com.stage.teamb.services.auth;


import com.stage.teamb.config.security.jwt.JwtService;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.models.Users;
import com.stage.teamb.repository.jpa.users.UsersRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        log.warn("Logout in Logout Handler Service ");
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            log.warn(" not found jwt in logout");
            return;
        }

        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(401, Collections.singletonList("User Not Allowed")));

        // Verify that the actual authenticated user is the same user in the method parameters
        if (!user.getEmail().equals(authentication.getName())) {
            throw new CustomException(401, Collections.singletonList("User Not Allowed"));
        }

        // Extract token from cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName()) ) {
                    jwtService.updateCookieExpiry(user); // Update cookieExpiry on logout
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
            SecurityContextHolder.clearContext();
    }



}