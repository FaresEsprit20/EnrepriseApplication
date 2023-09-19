package com.stage.teamb.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationDTO {
    private Long id;
    private String nom;
    private String description;
    private Long employeeId;
    private List<Long> ratingIds;
    private List<Long> eventIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
