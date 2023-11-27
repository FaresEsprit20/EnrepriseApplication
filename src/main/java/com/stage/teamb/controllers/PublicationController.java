package com.stage.teamb.controllers;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.event.EventDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.services.publication.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {


    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/{publicationId}/events")
    public ResponseEntity<List<EventDTO>> findEventsByPublicationId(@PathVariable Long publicationId) {
        List<EventDTO> events = publicationService.findEventsByPublicationId(publicationId);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/{publicationId}/events")
    public ResponseEntity<EventDTO> addEventToPublication(
            @PathVariable Long publicationId,
            @RequestBody EventDTO eventDTO) {
        EventDTO addedEvent = publicationService.addEventToPublication(publicationId, eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEvent);
    }

    @DeleteMapping("/{publicationId}/events/{eventId}")
    public void removeEventFromPublication(@PathVariable Long publicationId, @PathVariable Long eventId) {
        publicationService.removeEventFromPublication(publicationId, eventId);
    }

    @GetMapping("/events/{eventId}/publication")
    public PublicationDTO getPublicationByEventId(@PathVariable Long eventId) {
        return publicationService.findPublicationByEventId(eventId);
    }

    @GetMapping("/{id}/employee")
    public EmployeeDTO getEmployeeByPublicationId(@PathVariable Long id) {
        return publicationService.findEmployeeByPublicationId(id);
    }

    @PostMapping("/{id}/employee/{employeeId}")
    public PublicationDTO associateEmployeeWithPublication(@PathVariable Long id, @PathVariable Long employeeId) {
        return publicationService.associateEmployeeWithPublication(id, employeeId);
    }

    @DeleteMapping("/{id}/employee")
    public PublicationDTO disassociateEmployeeFromPublication(@PathVariable Long id) {
        return publicationService.disassociateEmployeeFromPublication(id);
    }



}
