package com.stage.teamb.services.auth;

import com.stage.teamb.dtos.auth.*;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Responsible;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.security.Principal;

public interface AuthenticationService {


    AuthenticationResponse registerEmployee(RegisterRequest request);

    AuthenticationResponse registerResponsible(RegisterRequest request);

    Employee buildEmployeeFromRequest(RegisterRequest request);

    Responsible buildResponsibleFromRequest(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    @PreAuthorize("hasAnyRole('RESPONSIBLE', 'EMPLOYEE')")
    void changePassword(ChangePasswordRequest request, Principal connectedUser);


    ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

    boolean isTokenExpired(String token);
}
