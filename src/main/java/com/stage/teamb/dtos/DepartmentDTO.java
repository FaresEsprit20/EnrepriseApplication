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
public class DepartmentDTO {
    private Long id;
    private String departmentName;
    private Long entrepriseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
