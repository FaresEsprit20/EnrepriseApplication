package com.stage.teamb.services.event;

import com.stage.teamb.dtos.event.EventDTO;
import com.stage.teamb.mappers.EventMapper;
import com.stage.teamb.models.Event;
import com.stage.teamb.models.Publication;
import com.stage.teamb.models.Responsible;
import com.stage.teamb.repository.jpa.event.EventRepository;
import com.stage.teamb.repository.jpa.publication.PublicationRepository;
import com.stage.teamb.repository.jpa.responsible.ResponsibleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final PublicationRepository publicationRepository;
    private final ResponsibleRepository responsibleRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PublicationRepository publicationRepository, ResponsibleRepository responsibleRepository) {
        this.eventRepository = eventRepository;
        this.publicationRepository = publicationRepository;
        this.responsibleRepository = responsibleRepository;
    }




    @Override
    public List<EventDTO> findAllEvents() {
        return EventMapper.toListDTO(eventRepository.findAll());
    }

    @Override
    public EventDTO findEventById(Long id) {
        return EventMapper.toDTO(eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public EventDTO saveEvent(EventDTO eventDTO) {
        try {
            Event event = EventMapper.toEntity(eventDTO);

            // Find the publication and responsible entities
            Publication publication = publicationRepository.findById(eventDTO.getPublicationId())
                    .orElseThrow(() -> new RuntimeException("Publication not found "));
            Responsible responsible = responsibleRepository.findById(eventDTO.getResponsibleID())
                    .orElseThrow(() -> new RuntimeException("Responsible not found "));
            // Set the publication and responsible using helper methods
            event.setPublicationForEvent(publication);
            event.setResponsibleForEvent(responsible);
            // Save the event
            Event savedEvent = eventRepository.save(event);
            return EventMapper.toDTO(savedEvent);
        } catch (Exception exception) {
            log.error("Could not save event: " + exception.getMessage());
            throw new RuntimeException("Could not save event: " + exception.getMessage());
        }
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) {
        try {
            Event existingEvent = eventRepository.findById(eventDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + eventDTO.getId()));
            // Find the publication and responsible entities
            Publication publication = publicationRepository.findById(eventDTO.getPublicationId())
                    .orElseThrow(() -> new RuntimeException("Publication not found with id " + eventDTO.getPublicationId()));
            Responsible responsible = responsibleRepository.findById(eventDTO.getResponsibleID())
                    .orElseThrow(() -> new RuntimeException("Responsible not found with id " + eventDTO.getResponsibleID()));
            // Update event fields
            existingEvent.setTitle(eventDTO.getTitle());
            existingEvent.setEventDate(eventDTO.getEventDate());
            // Associate the event with the new publication and responsible using helper methods
            existingEvent.setPublicationForEvent(publication);
            existingEvent.setResponsibleForEvent(responsible);
            // Save the updated event
            Event updatedEvent = eventRepository.save(existingEvent);
            return EventMapper.toDTO(updatedEvent);
        } catch (Exception exception) {
            log.error("Could not update event: " + exception.getMessage());
            throw new RuntimeException("Could not update event: " + exception.getMessage());
        }
    }

    @Override
    public void deleteEventById(Long id) {
        if (eventRepository.existsById(id)) {
            try {
                Event event = eventRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
                // Remove the event from the associated publication and responsible
                event.removePublicationForEvent();
                event.removeResponsibleForEvent();
                // Delete the event
                eventRepository.deleteById(id);
            } catch (Exception exception) {
                log.error("Can not delete this event: " + exception.getMessage());
                throw new RuntimeException("Can not delete this event: " + exception.getMessage());
            }
        } else {
            log.error("Event Not Exist");
            throw new RuntimeException("Event Not Exist");
        }
    }


    @Override
    public EventDTO addPublicationToEvent(Long eventId, Long publicationId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
            Publication publication = publicationRepository.findById(publicationId)
                    .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
            // Associate the event with the publication
            event.setPublicationForEvent(publication);
            // Save the updated event
            Event updatedEvent = eventRepository.save(event);
            return EventMapper.toDTO(updatedEvent);
        } catch (Exception exception) {
            log.error("Could not add publication to event: " + exception.getMessage());
            throw new RuntimeException("Could not add publication to event: " + exception.getMessage());
        }
    }

    @Override
    public EventDTO removePublicationFromEvent(Long eventId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
            // Remove the publication association from the event
            event.removePublicationForEvent();
            // Save the updated event
            Event updatedEvent = eventRepository.save(event);
            return EventMapper.toDTO(updatedEvent);
        } catch (Exception exception) {
            log.error("Could not remove publication from event: " + exception.getMessage());
            throw new RuntimeException("Could not remove publication from event: " + exception.getMessage());
        }
    }

    @Override
    public EventDTO addResponsibleToEvent(Long eventId, Long responsibleId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
            Responsible responsible = responsibleRepository.findById(responsibleId)
                    .orElseThrow(() -> new RuntimeException("Responsible not found with id " + responsibleId));
            // Associate the event with the responsible
            event.setResponsibleForEvent(responsible);
            // Save the updated event
            Event updatedEvent = eventRepository.save(event);
            return EventMapper.toDTO(updatedEvent);
        } catch (Exception exception) {
            log.error("Could not add responsible to event: " + exception.getMessage());
            throw new RuntimeException("Could not add responsible to event: " + exception.getMessage());
        }
    }

    @Override
    public EventDTO removeResponsibleFromEvent(Long eventId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
            // Remove the responsible association from the event
            event.removeResponsibleForEvent();
            // Save the updated event
            Event updatedEvent = eventRepository.save(event);
            return EventMapper.toDTO(updatedEvent);
        } catch (Exception exception) {
            log.error("Could not remove responsible from event: " + exception.getMessage());
            throw new RuntimeException("Could not remove responsible from event: " + exception.getMessage());
        }
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findOne(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event saveOne(Event Event) {
        return eventRepository.save(Event);
    }

    @Override
    public void deleteOne(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }




}