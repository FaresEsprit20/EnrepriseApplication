package com.stage.teamb.dtos.auth;

import com.stage.teamb.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private int registrationNumber;
    private LocalDate birthDate;
    private String email;
    private String password;
    private UserRole role;
    private Integer tel;
    private String occupation;
}