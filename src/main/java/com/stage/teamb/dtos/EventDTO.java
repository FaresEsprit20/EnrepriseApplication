package com.stage.teamb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EventDTO {
    private Long id;
    private String title;
    @JsonProperty("event_date")
    private LocalDateTime eventDate;
    private List<Long> publishedsId;
    private Long responsableID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
