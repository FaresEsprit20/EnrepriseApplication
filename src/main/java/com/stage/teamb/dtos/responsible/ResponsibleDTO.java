package com.stage.teamb.dtos.responsible;
import com.stage.teamb.dtos.enterprise.EnterpriseDTO;
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
public class ResponsibleDTO {
    private Long id;
    private String registrationNumber;
    private String name;
    private String lastName;
    private String occupation;
    private EnterpriseDTO enterpriseDTO;
    private List<Long> eventsId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

