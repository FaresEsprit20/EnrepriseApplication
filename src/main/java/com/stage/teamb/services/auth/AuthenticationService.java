package com.stage.teamb.services.auth;

import com.stage.teamb.dtos.auth.*;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Responsible;
import com.stage.teamb.models.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.security.Principal;

public interface AuthenticationService {

    AuthenticationResponse registerEmployee(RegisterRequest request, HttpServletResponse response);

    AuthenticationResponse registerResponsible(RegisterRequest request, HttpServletResponse response);

    Employee buildEmployeeFromRequest(RegisterRequest request);

    Responsible buildResponsibleFromRequest(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest loginRequest, HttpServletRequest request, HttpServletResponse response);


    @PreAuthorize("hasAnyRole('RESPONSIBLE', 'EMPLOYEE')")
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

    boolean isTokenExpired(String token);

    //void saveTokenInCookie(HttpServletResponse response, String jwtToken);
    boolean isUserOnline();
    UserRole getAuthUserRole(String email);
//
//
    Authentication getAuthenticationSecurityContext();
//
//    boolean isValidUserAction(String email);
//
//    boolean isValidUserIdentifierAction(Long identifier);



}
