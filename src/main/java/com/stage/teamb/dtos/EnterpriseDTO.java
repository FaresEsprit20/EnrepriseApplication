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
public class EnterpriseDTO {
    private Long id;
    private String nomEntreprise;
    private String localEntreprise;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
