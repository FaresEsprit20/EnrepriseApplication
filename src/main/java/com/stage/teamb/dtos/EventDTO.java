package com.stage.teamb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {
    private Long id;
    private String title;
    @JsonProperty("event_date")
    private LocalDateTime eventDate;
    private Long publicationId;
    private Long responsibleID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
