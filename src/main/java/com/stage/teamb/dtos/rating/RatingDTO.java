package com.stage.teamb.dtos.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDTO {
    private Long id;
    private Boolean value;
    private Long employeeId;
    private Long publicationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
