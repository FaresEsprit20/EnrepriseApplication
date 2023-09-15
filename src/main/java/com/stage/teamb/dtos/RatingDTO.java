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
public class RatingDTO {
    private Long id;
    private Boolean value;
    private List<Long> publishedsId;
    private Long employeeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
