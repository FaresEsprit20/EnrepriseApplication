package com.stage.teamb.services.auth;

import com.stage.teamb.config.security.jwt.JwtService;
import com.stage.teamb.dtos.auth.*;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Responsible;
import com.stage.teamb.models.Users;
import com.stage.teamb.models.enums.UserRole;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
import com.stage.teamb.repository.jpa.responsible.ResponsibleRepository;
import com.stage.teamb.repository.jpa.users.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

   private final EmployeeRepository employeeRepository;
   private final ResponsibleRepository responsibleRepository;
   private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse registerEmployee(RegisterRequest request) {
            var employee = buildEmployeeFromRequest(request);
          var savedUser = employeeRepository.save(employee);
        var jwtToken = jwtService.generateToken(employee);
        var refreshToken = jwtService.generateRefreshToken(employee);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Override
    public AuthenticationResponse registerResponsible(RegisterRequest request) {
        var responsible = buildResponsibleFromRequest(request);
        var savedUser = responsibleRepository.save(responsible);
        var jwtToken = jwtService.generateToken(responsible);
        var refreshToken = jwtService.generateRefreshToken(responsible);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Override
    public Employee buildEmployeeFromRequest(RegisterRequest request) {
        return Employee.builder()
                .name(request.getFirstname())
                .registrationNumber(request.getRegistrationNumber())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.EMPLOYEE)
                .birthDate(request.getBirthDate())
                .occupation(request.getOccupation())
                .tel(request.getTel())
                .build();
    }
    @Override
    public Responsible buildResponsibleFromRequest(RegisterRequest request) {
        return Responsible.builder()
                .name(request.getFirstname())
                .registrationNumber(request.getRegistrationNumber())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.EMPLOYEE)
                .birthDate(request.getBirthDate())
                .occupation(request.getOccupation())
                .tel(request.getTel())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<Users> user = Optional.ofNullable(usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(401, Collections.singletonList("User Not Found"))));
        if(user.isPresent()){
            log.warn("present");
        }else {
            log.warn("not present");
            throw new BadCredentialsException("Wrong credentials");
        }
        var jwtToken = jwtService.generateToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(user.get());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @PreAuthorize("hasAnyRole('RESPONSIBLE', 'EMPLOYEE')")
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (Users) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        usersRepository.save(user);
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        // Check if the authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Return a 401 Unauthorized response if the authorization header is missing or invalid
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        // Check if user email is null
        if (userEmail == null) {
            // Return a 401 Unauthorized response if the user email cannot be extracted
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Retrieve the user from the repository
        var user = this.usersRepository.findByEmail(userEmail)
                .orElseThrow();

        // Check if the refresh token is valid for the user
        if (jwtService.isTokenValid(refreshToken, user)) {
            // Generate a new access token and perform other necessary operations
            var accessToken = jwtService.generateToken(user);
            // Build and return the RefreshTokenResponse
            var authResponse = RefreshTokenResponse.builder()
                    .refreshToken(refreshToken)
                    .build();
            return ResponseEntity.ok(authResponse);
        }
        // Return a 401 Unauthorized response if the refresh token is not valid
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @Override
    public boolean isTokenExpired(String token) {
    return jwtService.isTokenExpired(token);
    }


}