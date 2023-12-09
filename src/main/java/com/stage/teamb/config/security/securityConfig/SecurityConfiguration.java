package com.stage.teamb.config.security.securityConfig;

import com.stage.teamb.config.security.jwt.JwtAuthenticationFilter;
import com.stage.teamb.models.enums.UserRole;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors
                        .configurationSource(getCorsConfigurationSource()))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                //addresses
                                .requestMatchers("/api/addresses/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                //departments
                                .requestMatchers("/api/departments/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                //employees
                                .requestMatchers(GET, "/api/employees/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                .requestMatchers(POST, "/api/employees/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                .requestMatchers(PUT, "/api/employees/**").hasAnyRole(UserRole.RESPONSIBLE.name(), UserRole.EMPLOYEE.name())
                                .requestMatchers(DELETE, "/api/employees/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                //enterprises
                                .requestMatchers(GET, "/api/enterprises/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                .requestMatchers(POST, "/api/enterprises/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                .requestMatchers(PUT, "/api/enterprises/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                .requestMatchers(DELETE, "/api/enterprises/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                //publications
                                .requestMatchers(GET, "/api/publications/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                .requestMatchers(POST, "/api/publications/**").hasAnyRole(UserRole.EMPLOYEE.name())
                                .requestMatchers(PUT, "/api/publications/**").hasAnyRole(UserRole.EMPLOYEE.name())
                                .requestMatchers(DELETE, "/api/publications/**").hasAnyRole(UserRole.EMPLOYEE.name())
                                //ratings
                                .requestMatchers(GET, "/api/ratings/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                .requestMatchers(POST, "/api/ratings/**").hasAnyRole(UserRole.EMPLOYEE.name())
                                .requestMatchers(PUT, "/api/ratings/**").hasAnyRole(UserRole.EMPLOYEE.name())
                                .requestMatchers(DELETE, "/api/ratings/**").hasAnyRole(UserRole.EMPLOYEE.name())
                                //responsibles
                                .requestMatchers("/api/responsibles/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .rememberMe((remember) -> remember
                        .rememberMeServices(rememberMeServices)
                )
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    log.warn("Logout in Security Configuration");
                                    // Extract token from cookies
                                    Cookie[] cookies = request.getCookies();
                                    if (cookies != null) {
                                        for (Cookie cookie : cookies) {
                                            if ("accessToken".equals(cookie.getName())) {
                                                cookie.setMaxAge(0);
                                                response.addCookie(cookie);
                                                break;
                                            }
                                        }
                                    }
                                    logout.clearAuthentication(true);
                                    logout.deleteCookies("accessToken");
                                    SecurityContextHolder.clearContext();
                                })
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of(
                "Authorization", "Cache-Control", "Content-Type",
                "Origin, X-Requested-With,  Accept, Key"));
        configuration.setExposedHeaders(List.of(
                "Authorization", "Cache-Control", "Content-Type",
                "Origin, X-Requested-With,  Accept, Key"));
        configuration.setAllowedMethods(Arrays.asList(
                "MKCALENDAR", "MKCOL", "MOVE", "PROPFIND", "PROPPATCH",
                "REPORT", "SEARCH", "UNCHECKOUT", "UNLOCK", "UPDATE", "VERSION-CONTROL",
                "ACL", "CANCELUPLOAD", "CHECKIN", "CHECKOUT", "COPY",
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE", "HEAD"));
        configuration.applyPermitDefaultValues();
        // Use allowedOriginPatterns instead of allowedOrigins
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        // You can still keep setAllowCredentials(true)
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("keyForRememberMeToken", userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }


}