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
public class PublicationDTO {
    private Long id;
    private String name;
    private String description;
    private Long employeeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
