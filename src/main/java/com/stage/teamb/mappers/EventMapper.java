package com.stage.teamb.mappers;

import com.stage.teamb.models.Event;
import com.stage.teamb.dtos.EventDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
public class EventMapper {

    public static EventDTO toDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .titre(event.getTitre())
                .responsableID(event.getResponsible() != null ? event.getResponsible().getId() : null)
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    public static List<EventDTO> toListDTO(List<Event> eventList) {
        return eventList.stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Event toEntity(EventDTO eventDTO) {
        return Event.builder()
                .id(eventDTO.getId())
                .titre(eventDTO.getTitre())
                .createdAt(eventDTO.getCreatedAt())
                .updatedAt(eventDTO.getUpdatedAt())
                .build();
    }

    public static List<Event> toListEntity(List<EventDTO> eventDTOList) {
        return eventDTOList.stream()
                .map(EventMapper::toEntity)
                .collect(Collectors.toList());
    }
}
