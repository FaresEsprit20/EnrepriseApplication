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
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public AuthenticationResponse registerEmployee(RegisterRequest request, HttpServletResponse response) {
        var employee = buildEmployeeFromRequest(request);
        var savedUser = employeeRepository.save(employee);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        // Save the token in a cookie
        saveTokenInCookie(response, jwtToken);
        return buildAuthResponse(jwtToken, refreshToken);
    }

    @Override
    public AuthenticationResponse registerResponsible(RegisterRequest request, HttpServletResponse response) {
        var responsible = buildResponsibleFromRequest(request);
        var savedUser = responsibleRepository.save(responsible);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        // Save the token in a cookie
        saveTokenInCookie(response, jwtToken);
        return buildAuthResponse(jwtToken, refreshToken);
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
                .role(UserRole.RESPONSIBLE)
                .birthDate(request.getBirthDate())
                .occupation(request.getOccupation())
                .tel(request.getTel())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<Users> user = Optional.ofNullable(usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(401, Collections.singletonList("User Not Found"))));
        if (user.isPresent()) {
            log.warn("User is present");
        } else {
            log.warn("User is not present");
            throw new BadCredentialsException("Wrong credentials");
        }
        var jwtToken = jwtService.generateToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(user.get());
        // Save the token in a cookie
        saveTokenInCookie(response, jwtToken);
        return buildAuthResponse(jwtToken, refreshToken);
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
            throw new IllegalStateException("Passwords are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        usersRepository.save(user);
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Return a 401 Unauthorized response if the authorization header is missing or invalid
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String refreshToken = authHeader.substring(7);

        // Extract the user's email from the refresh token
        String userEmail = jwtService.extractUsername(refreshToken);

        // Check if user email is null or not found
        if (userEmail == null) {
            // Return a 401 Unauthorized response if the user email cannot be extracted
           throw new CustomException(403,Collections.singletonList("Invalid Email"));
        }

        // Retrieve the user from the repository
        Optional<Users> userOptional = this.usersRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            // User not found
            throw new CustomException(403,Collections.singletonList("User Not found"));
        }

        Users user = userOptional.get();

        // Check if the refresh token is valid for the user
        if (jwtService.isTokenValid(refreshToken, user)) {
            // Generate a new access token and perform other necessary operations
            var newAccessToken = jwtService.generateToken(user);

            // Rotate refresh token (optional)
            var newRefreshToken = jwtService.generateRefreshToken(user);

            // Save the new tokens in cookies
            saveTokenInCookie(response, newAccessToken);

            // Build and return the RefreshTokenResponse
            var authResponse = RefreshTokenResponse.builder()
                    .refreshToken(newRefreshToken)
                    .build();

            return ResponseEntity.ok(authResponse);
        }

        // Return a 401 Unauthorized response if the refresh token is not valid
        throw new CustomException(403, Collections.singletonList("Invalid refresh token"));
    }


    @Override
    public boolean isTokenExpired(String token) {
        return jwtService.isTokenExpired(token);
    }

    @Override
    public void saveTokenInCookie(HttpServletResponse response, String jwtToken) {
        // Set the Max-Age attribute to 3 hours (in seconds)
        long maxAgeInSeconds = jwtService.getJwtExpiration() / 1000;
        // Set the token as an HttpOnly cookie in the response
        ResponseCookie cookie = ResponseCookie.from("accessToken", jwtToken)
                .httpOnly(true)
                .secure(false)  // Change this to 'true' in a production environment if using HTTPS
                .path("/")
                .maxAge(maxAgeInSeconds)  // setMaxAge expects seconds, so we convert milliseconds to seconds
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        // Log cookie information
        log.info("JWT cookie set: Name={}, Value={}, MaxAge={}, Path={}", cookie.getName(), "*****", cookie.getMaxAge(), cookie.getPath());
    }

    @Override
    public boolean isUserOnline(String email) {
        // Retrieve the authenticated user's email from the SecurityContext
        if (isValidUserAction(email)) {
            return true;
        } else {
            throw new CustomException(401, Collections.singletonList("User Not Found"));
        }
    }

    @Override
    public UserRole getAuthUserRole(String email) {
        if(isValidUserAction(email)) {
           Users user = usersRepository.findByEmail(email)
                   .orElseThrow(() -> new CustomException(401, Collections.singletonList("User Not Found")));
            return user.getRole();
        }
        throw new CustomException(401, Collections.singletonList("User Not Authenticated"));
    }

    private AuthenticationResponse buildAuthResponse(String accessToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Authentication getAuthenticationSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean isValidUserAction(String email) {
        return getUserDetails()
                .map(userDetails -> userDetails.getUsername().equals(email))
                .orElse(false);
    }

    @Override
    public boolean isValidUserIdentifierAction(Long identifier) {
        Authentication authentication = getAuthenticationSecurityContext();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            Optional<Users> targetUser = usersRepository.findById(identifier);
            return targetUser.map(users -> users.getEmail().equals(userDetails.getUsername())).orElse(false);
        }
        return false;
    }

    @Override
    public Optional<UserDetails> getUserDetails() {
        Authentication authentication = getAuthenticationSecurityContext();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return Optional.of((UserDetails) authentication.getPrincipal());
        }
        return Optional.empty();
    }


}
