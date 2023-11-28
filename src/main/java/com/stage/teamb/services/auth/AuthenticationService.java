package com.stage.teamb.services.auth;

import com.stage.teamb.dtos.auth.AuthenticationRequest;
import com.stage.teamb.dtos.auth.AuthenticationResponse;
import com.stage.teamb.dtos.auth.ChangePasswordRequest;
import com.stage.teamb.dtos.auth.RegisterRequest;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Responsible;
import com.stage.teamb.models.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    void saveUserToken(Users user, String jwtToken);

    void revokeAllUserTokens(Users user);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

}
