package com.stage.teamb.controllers;//package com.stage.teamb.controllers;
//
//import com.stage.teamb.dtos.EmployeeDTO;
//import com.stage.teamb.mappers.EmployeeMapper;
//import com.stage.teamb.mappers.PublishedMapper;
//import com.stage.teamb.models.*;
//import com.stage.teamb.dtos.PublishedDTO;
//import com.stage.teamb.services.*;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/publication")
//public class PublicationController {
//
//    private final PublicationService publicationService;
//    private final RatingService ratingService;
//    private final EmployeeService employeeService;
//    private final EventService eventService;
//
//    @Autowired
//    public PublicationController(PublicationService publicationService, RatingService ratingService, EmployeeService employeeService, EventService eventService) {
//        this.publicationService = publicationService;
//        this.ratingService = ratingService;
//        this.employeeService = employeeService;
//        this.eventService = eventService;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PublishedDTO> findOne(@PathVariable Long id) {
//        Optional<PublishedDTO> publication = publicationService.findOne(id);
//        return publication.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PublishedDTO>> findAll() {
//        List<PublishedDTO> publications = publicationService.findAll();
//        return ResponseEntity.ok(publications);
//    }
//
//    @PostMapping
//    public ResponseEntity<PublishedDTO> saveOne(@io.swagger.v3.oas.annotations.parameters.RequestBody PublishedDTO publishedDTO) {
//        PublishedDTO savedPublication = publicationService.saveOne(publishedDTO);
//        return ResponseEntity.ok(savedPublication);
//    }
//
//    @PostMapping("/addEmployee")
//    public ResponseEntity<String> addEmployeeToPublication(@io.swagger.v3.oas.annotations.parameters.RequestBody PublishedDTO publishedDTO) {
//        Optional<EmployeeDTO> employeeOptional = employeeService.findOne(publishedDTO.getEmployeeId());
//        if (employeeOptional.isPresent()) {
//            Published published = PublishedMapper.toEntity(publishedDTO);
//            published.setEmployee(EmployeeMapper.toEntity(employeeOptional.get()));
//            publicationService.saveOne(PublishedMapper.toDTO(published));
//            return ResponseEntity.ok("Employee added to publication.");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
////    @PostMapping("/addRating")
////    public ResponseEntity<String> addRatingToPublication(@io.swagger.v3.oas.annotations.parameters.RequestBody PublishedDTO publishedDTO) {
////        List<Rating> ratingOptional = ratingService.findRatings(publishedDTO.getRatingIds());
////        if (ratingOptional.isEmpty()) {
////            Published published = publicationMapper.toPublishedEntity(publishedDTO);
////            published.setRating(ratingOptional);
////            publicationService.save(published);
////            return ResponseEntity.ok("Rating added to publication.");
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
//
////    @PostMapping("/addEvent")
////    public ResponseEntity<String> addEventToPublication(@io.swagger.v3.oas.annotations.parameters.RequestBody PublishedDTO publishedDTO) {
////        List<Event> events = eventService.findEvents(publishedDTO.getEventIds());
////        if (!events.isEmpty()) {
////            Published published = publicationMapper.toPublishedEntity(publishedDTO);
////            published.setEvents(events);
////            publicationService.save(published);
////            return ResponseEntity.ok().build();
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
//
//
//    @PutMapping
//    public ResponseEntity<PublishedDTO> update(@RequestBody PublishedDTO publishedDTO) {
//        PublishedDTO updatedPublication = publicationService.saveOne(publishedDTO);
//        return ResponseEntity.ok(updatedPublication);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOne(@PathVariable Long id) {
//        publicationService.deleteOne(id);
//        return ResponseEntity.noContent().build();
//    }
//}


import com.stage.teamb.dtos.PublishedDTO;
import com.stage.teamb.services.PublicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
@Slf4j
public class PublicationController {

    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllPublications() {
        try {
            List<PublishedDTO> publishedDTOList = publicationService.findAllPublications();
            return ResponseEntity.ok(publishedDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving publications: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving publications: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicationById(@PathVariable Long id) {
        try {
            PublishedDTO publishedDTO = publicationService.findPublishedById(id);
            return ResponseEntity.ok(publishedDTO);
        } catch (RuntimeException exception) {
            log.error("Publication not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Publication not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createPublication(@RequestBody PublishedDTO publishedDTO) {
        try {
            PublishedDTO createdPublication = publicationService.savePublished(publishedDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPublication);
        } catch (RuntimeException exception) {
            log.error("Could not create publication: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create publication: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublication(@PathVariable Long id, @RequestBody PublishedDTO publishedDTO) {
        try {
            publishedDTO.setId(id); // Ensure the ID matches the path variable
            PublishedDTO updatedPublication = publicationService.updatePublished(publishedDTO);
            return ResponseEntity.ok(updatedPublication);
        } catch (RuntimeException exception) {
            log.error("Could not update publication: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update publication: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublicationById(@PathVariable Long id) {
        try {
            publicationService.deletePublishedById(id);
            return ResponseEntity.ok("Publication deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete publication: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete publication: " + exception.getMessage());
        }
    }



}
