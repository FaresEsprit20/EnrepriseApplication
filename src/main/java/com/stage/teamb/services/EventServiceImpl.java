package com.stage.teamb.services;

import com.stage.teamb.dtos.EventDTO;
import com.stage.teamb.mappers.EventMapper;
import com.stage.teamb.models.Event;
import com.stage.teamb.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
            return EventMapper.toDTO(eventRepository.save(EventMapper.toEntity(eventDTO)));
        }catch (Exception exception){
            log.error("Address with not found.");
            throw new RuntimeException("Can not save this entity  :   "+exception.getMessage());
        }
    }

    @Override
    public void deleteEventById(Long id) {
        if (eventRepository.existsById(id)) {
            try{
                eventRepository.deleteById(id);
            }catch (Exception exception) {
                log.error("Can not delete this entity"+exception.getMessage());
                throw new RuntimeException("Can not delete this entity  :   "+exception.getMessage());
            }
        } else {
            log.error("Entity Not Exist");
            throw new RuntimeException("Entity Not Exist");
        }
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) {
        Event existingEvent= eventRepository.findById(eventDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + eventDTO.getId());
                });
        existingEvent.setTitle(eventDTO.getTitle());
        existingEvent.setEventDate(eventDTO.getEventDate());
        try {
            return EventMapper.toDTO(eventRepository.save(existingEvent));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
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