package com.stage.teamb.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Long departementId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


