package com.stage.teamb.config.security.securityConfig;

import com.stage.teamb.config.security.jwt.JwtAuthenticationFilter;
import com.stage.teamb.models.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                //addresses
                                .requestMatchers("/api/addresses/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                //departments
                                .requestMatchers("/api/departments/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                //employees
                                .requestMatchers(GET,"/api/employees/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                .requestMatchers(POST,"/api/employees/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                .requestMatchers(PUT,"/api/employees/**").hasAnyRole(UserRole.RESPONSIBLE.name(), UserRole.EMPLOYEE.name())
                                .requestMatchers(DELETE,"/api/employees/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                //enterprises
                                .requestMatchers(GET,"/api/enterprises/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                .requestMatchers(POST,"/api/enterprises/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                .requestMatchers(PUT,"/api/enterprises/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                .requestMatchers(DELETE,"/api/enterprises/**").hasAnyRole(UserRole.RESPONSIBLE.name())
                                //events
                                .requestMatchers(GET,"/api/events/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                .requestMatchers(POST,"/api/events/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                //publications
                                .requestMatchers(GET,"/api/publications/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                //ratings
                                .requestMatchers(GET,"/api/ratings/**").hasAnyRole(UserRole.EMPLOYEE.name(), UserRole.RESPONSIBLE.name())
                                //responsibles
                                .requestMatchers(GET,"/api/responsibles/**").hasAnyRole(UserRole.RESPONSIBLE.name())
//                                .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

}