package com.stage.teamb.dtos.publication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationGetDTO {

    private Long id;
    private String name;
    private String description;
    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private boolean userVoted;
    private boolean vote;
    private Long upVotes;
    private Long downVotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

