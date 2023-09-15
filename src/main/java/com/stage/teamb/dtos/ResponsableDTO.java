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
public class ResponsableDTO {
    private Long id;
    private int matricule;
    private String nom;
    private String prenom;
    private List<Long> eventsId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

