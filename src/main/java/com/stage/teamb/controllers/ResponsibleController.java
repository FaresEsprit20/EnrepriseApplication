package com.stage.teamb.controllers;//package com.stage.teamb.controllers;
//
//import com.stage.teamb.mappers.ResponsibleMapper;
//import com.stage.teamb.models.*;
//import com.stage.teamb.dtos.ResponsableDTO;
//import com.stage.teamb.services.EventService;
//import com.stage.teamb.services.ResponsibleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/responsable")
//public class ResponsableController {
//    private final ResponsibleService responsableService;
//    private final EventService eventService;
//
//    @Autowired
//    public ResponsableController(ResponsibleService responsableService, EventService eventService) {
//        this.responsableService = responsableService;
//        this.eventService = eventService;
//    }
//
//    @GetMapping("/{id}")
//    public Optional<ResponsableDTO> findOne(@PathVariable Long id) {
//        return (responsableService.findOne(id));
//    }
//
//    @GetMapping()
//    public Optional<List<ResponsableDTO>> findAll() {
//        return Optional.ofNullable(responsableService.findAll());
//    }
//
//
//    @PostMapping("/addEvent")
//    public ResponseEntity<String> addEventToResponsable(@RequestBody ResponsableDTO responsableDTO) {
//        List<Event> events = eventService.findEventsByIds(responsableDTO.getEventsId());
//        if (!events.isEmpty()) {
//            Responsible responsable = ResponsibleMapper.toEntity(responsableDTO);
//            responsable.setEvents(events);
//            responsableService.saveOne(ResponsibleMapper.toDTO(responsable));
//            return ResponseEntity.ok("Event added to publication.");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//    @PutMapping("/update")
//    public ResponsableDTO update(@RequestBody ResponsableDTO responsable) {
//        return responsableService.saveOne(responsable);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteOne(@PathVariable Long id) {
//        responsableService.deleteOne(id);
//    }
//}
//


import com.stage.teamb.dtos.ResponsibleDTO;
import com.stage.teamb.services.ResponsibleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsibles")
@Slf4j
public class ResponsibleController {

    private final ResponsibleService responsibleService;

    @Autowired
    public ResponsibleController(ResponsibleService responsibleService) {
        this.responsibleService = responsibleService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllResponsibles() {
        try {
            List<ResponsibleDTO> responsableDTOList = responsibleService.findAllResponsibles();
            return ResponseEntity.ok(responsableDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving responsibles: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving responsibles: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResponsibleById(@PathVariable Long id) {
        try {
            ResponsibleDTO responsableDTO = responsibleService.findResponsibleById(id);
            return ResponseEntity.ok(responsableDTO);
        } catch (RuntimeException exception) {
            log.error("Responsible not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Responsible not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createResponsible(@RequestBody ResponsibleDTO responsableDTO) {
        try {
            ResponsibleDTO createdResponsible = responsibleService.saveResponsible(responsableDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResponsible);
        } catch (RuntimeException exception) {
            log.error("Could not create responsible: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create responsible: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResponsible(@PathVariable Long id, @RequestBody ResponsibleDTO responsableDTO) {
        try {
            responsableDTO.setId(id); // Ensure the ID matches the path variable
            ResponsibleDTO updatedResponsible = responsibleService.updateResponsible(responsableDTO);
            return ResponseEntity.ok(updatedResponsible);
        } catch (RuntimeException exception) {
            log.error("Could not update responsible: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update responsible: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResponsibleById(@PathVariable Long id) {
        try {
            responsibleService.deleteResponsibleById(id);
            return ResponseEntity.ok("Responsible deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete responsible: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete responsible: " + exception.getMessage());
        }
    }




}
