package com.stage.teamb.services.event;

import com.stage.teamb.dtos.event.EventDTO;
import com.stage.teamb.models.Event;

import java.util.List;
import java.util.Optional;

public interface EventService  {


    List<EventDTO> findAllEvents();

    EventDTO findEventById(Long id);

    EventDTO saveEvent(EventDTO eventDTO);

    void deleteEventById(Long id);

    EventDTO updateEvent(EventDTO eventDTO);

    EventDTO addPublicationToEvent(Long eventId, Long publicationId);

    EventDTO removePublicationFromEvent(Long eventId);

    EventDTO addResponsibleToEvent(Long eventId, Long responsibleId);

    EventDTO removeResponsibleFromEvent(Long eventId);

    List<Event> findAll();

    Optional<Event> findOne(Long id);

    Event saveOne(Event Event);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}