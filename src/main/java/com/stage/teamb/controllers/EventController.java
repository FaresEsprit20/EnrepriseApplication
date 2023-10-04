package com.stage.teamb.controllers;

import com.stage.teamb.dtos.event.EventDTO;
import com.stage.teamb.services.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Slf4j
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllEvents() {
        try {
            List<EventDTO> eventDTOList = eventService.findAllEvents();
            return ResponseEntity.ok(eventDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving events: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving events: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            EventDTO eventDTO = eventService.findEventById(id);
            return ResponseEntity.ok(eventDTO);
        } catch (RuntimeException exception) {
            log.error("Event not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            EventDTO createdEvent = eventService.saveEvent(eventDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (RuntimeException exception) {
            log.error("Could not create event: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create event: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        try {
            eventDTO.setId(id); // Ensure the ID matches the path variable
            EventDTO updatedEvent = eventService.updateEvent(eventDTO);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException exception) {
            log.error("Could not update event: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update event: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Long id) {
        try {
            eventService.deleteEventById(id);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete event: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete event: " + exception.getMessage());
        }
    }


}
