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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

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
    private final HttpSessionSecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    private final SecurityContextHolderStrategy contextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();
    @Override
    public AuthenticationResponse registerEmployee(RegisterRequest request, HttpServletResponse response) {
        if(!request.getPassword().equals(request.getPasswordMatches())){
            throw new CustomException(400, Collections.singletonList("Passwords mismatch"));
        }
        var employee = buildEmployeeFromRequest(request);
        var savedUser = employeeRepository.save(employee);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        return buildAuthResponse(jwtToken, refreshToken);
    }

    @Override
    public AuthenticationResponse registerResponsible(RegisterRequest request, HttpServletResponse response) {
        if(!request.getPassword().equals(request.getPasswordMatches())){
            throw new CustomException(400, Collections.singletonList("Passwords mismatch"));
        }
        var responsible = buildResponsibleFromRequest(request);
        var savedUser = responsibleRepository.save(responsible);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        return buildAuthResponse(jwtToken, refreshToken);
    }

    @Override
    public Employee buildEmployeeFromRequest(RegisterRequest request) {
        return Employee.builder()
                .name(request.getFirstname())
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
    public AuthenticationResponseDetails authenticate(AuthenticationRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        // Set the Authentication object in SecurityContextHolder
        var securityContext = this.contextHolderStrategy.createEmptyContext();
        securityContext.setAuthentication(authentication);
        // Save the SecurityContext using the SecurityContextRepository
        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

        Optional<Users> user = Optional.ofNullable(usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(401, Collections.singletonList("User Not Found"))));
        if (user.isPresent()) {
            log.warn("User is present");
        } else {
            log.warn("User is not present");
            throw new BadCredentialsException("Wrong credentials");
        }
        var jwtToken = jwtService.generateToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(user.get());
        log.warn("Authentication status  "+authentication.getPrincipal()+"  name  "+authentication.getName()
                + "  details  "+ authentication.getDetails()+" autorities  "+authentication.getAuthorities()
                +" email from jwt "+jwtService.extractUsername(jwtToken)+ "  ");
        // Save the token in a cookie
        //saveTokenInCookie(response, jwtToken);
        log.debug("JWT Token generated. Expiry: {}", jwtService.extractExpiration(jwtToken));
        return buildAuthResponseDetails(jwtToken, refreshToken,user.get().getRole().name(),
                user.get().getId(),user.get().getEmail());
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
    public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
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
           // saveTokenInCookie(response, newAccessToken);
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

//    @Override
//    public void saveTokenInCookie(HttpServletResponse response, String jwtToken) {
//        // Set the Max-Age attribute to 3 hours (in seconds)
//        long maxAgeInSeconds = jwtService.getJwtExpiration() / 1000;
//        // Set the token as an HttpOnly cookie in the response
//        ResponseCookie cookie = ResponseCookie.from("accessToken", jwtToken)
//                .httpOnly(true)
//                .secure(false)  // Change this to 'true' in a production environment if using HTTPS
//                .path("/")
//                .maxAge(maxAgeInSeconds)  // setMaxAge expects seconds, so we convert milliseconds to seconds
//                .build();
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//        // Log cookie information
//        log.info("JWT cookie set: Name={}, Value={}, MaxAge={}, Path={}", cookie.getName(), "*****", cookie.getMaxAge(), cookie.getPath());
//    }

    @Override
    public boolean isUserOnline() {
        Authentication authentication = getAuthContext();
        log.warn(" auth "+authentication.isAuthenticated()+ "  Authentication name  "+authentication.getName());
        return authentication.isAuthenticated();
    }

    @Override
    public UserRole getAuthUserRole(String email) {
        if(email != null) {
           Users user = usersRepository.findByEmail(email)
                   .orElseThrow(() -> new CustomException(401, Collections.singletonList("User Not Found")));
            return user.getRole();
        }
        throw new CustomException(401, Collections.singletonList("User Not Authenticated"));
    }

    private AuthenticationResponseDetails buildAuthResponseDetails(String accessToken, String refreshToken
            ,  String role, Long userId, String email) {
        return AuthenticationResponseDetails.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .role(role)
                .email(email)
                .build();
    }

    private AuthenticationResponse buildAuthResponse(String accessToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Authentication getAuthenticationSecurityContext() {
        log.warn("getAuthenticationSecurityContext() "+SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return SecurityContextHolder.getContext().getAuthentication();
    }

//    @Override
//    public boolean isValidUserAction(String email) {
//        return getUserDetails()
//                .map(userDetails -> userDetails.getUsername().equals(email))
//                .orElse(false);
//    }

//    @Override
//    public boolean isValidUserIdentifierAction(Long identifier) {
//        Authentication authentication = getAuthenticationSecurityContext();
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
//            Optional<Users> targetUser = usersRepository.findById(identifier);
//            return targetUser.map(users -> users.getEmail().equals(userDetails.getUsername())).orElse(false);
//        }
//        return false;
//    }

//    @Override
//    public Optional<UserDetails> getUserDetails() {
//        Authentication authentication = getAuthenticationSecurityContext();
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            return Optional.of((UserDetails) authentication.getPrincipal());
//        }
//        return Optional.empty();
//    }

    public Authentication getAuthContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
