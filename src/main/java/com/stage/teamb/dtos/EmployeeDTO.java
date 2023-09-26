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
    private int registrationNumber;
    private String name;
    private String lastName;
    private String email;
    private Long departmentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


