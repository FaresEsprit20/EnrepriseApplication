package com.stage.teamb.controllers.auth;

import com.stage.teamb.dtos.auth.*;
import com.stage.teamb.services.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register/employee")
    public ResponseEntity<AuthenticationResponse> registerEmployee(
            @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.registerEmployee(request, response));
    }

    @PostMapping("/register/responsible")
    public ResponseEntity<AuthenticationResponse> registerResponsible(
            @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.registerResponsible(request, response));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.authenticate(loginRequest, request, response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> getRefreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return authService.refreshToken(request, response);
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        authService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/is-token-expired")
    public ResponseEntity<Boolean> isTokenExpired(
            @RequestHeader("Authorization") String token
    ) {
        boolean isExpired = authService.isTokenExpired(token.replace("Bearer ", ""));
        return ResponseEntity.ok(isExpired);
    }



}
