package com.stage.teamb.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.teamb.config.security.jwt.JwtService;
import com.stage.teamb.config.security.token.Token;
import com.stage.teamb.config.security.token.TokenRepository;
import com.stage.teamb.config.security.token.TokenType;
import com.stage.teamb.dtos.auth.AuthenticationRequest;
import com.stage.teamb.dtos.auth.AuthenticationResponse;
import com.stage.teamb.dtos.auth.RegisterRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

   private final EmployeeRepository employeeRepository;
   private final ResponsibleRepository responsibleRepository;
   private final UsersRepository usersRepository;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse registerEmployee(RegisterRequest request) {
            var employee = buildEmployeeFromRequest(request);
          var savedUser = employeeRepository.save(employee);
        var jwtToken = jwtService.generateToken(employee);
        var refreshToken = jwtService.generateRefreshToken(employee);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse registerResponsible(RegisterRequest request) {
        var responsible = buildResponsibleFromRequest(request);
        var savedUser = responsibleRepository.save(responsible);
        var jwtToken = jwtService.generateToken(responsible);
        var refreshToken = jwtService.generateRefreshToken(responsible);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

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
        }
        var jwtToken = jwtService.generateToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(user.get());
        revokeAllUserTokens(user.get());
        saveUserToken(user.get(), jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Users user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.usersRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}