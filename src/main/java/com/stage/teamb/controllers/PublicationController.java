//package com.stage.teamb.controllers;//package com.stage.teamb.controllers;
////
////import com.stage.teamb.dtos.EmployeeDTO;
////import com.stage.teamb.mappers.EmployeeMapper;
////import com.stage.teamb.mappers.publicationedMapper;
////import com.stage.teamb.models.*;
////import com.stage.teamb.dtos.publicationedDTO;
////import com.stage.teamb.services.*;
////import io.swagger.v3.oas.annotations.parameters.RequestBody;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.Optional;
////
////@RestController
////@RequestMapping("/publication")
////public class PublicationController {
////
////    private final PublicationService publicationService;
////    private final RatingService ratingService;
////    private final EmployeeService employeeService;
////    private final EventService eventService;
////
////    @Autowired
////    public PublicationController(PublicationService publicationService, RatingService ratingService, EmployeeService employeeService, EventService eventService) {
////        this.publicationService = publicationService;
////        this.ratingService = ratingService;
////        this.employeeService = employeeService;
////        this.eventService = eventService;
////    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<publicationedDTO> findOne(@PathVariable Long id) {
////        Optional<publicationedDTO> publication = publicationService.findOne(id);
////        return publication.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
////    }
////
////    @GetMapping
////    public ResponseEntity<List<publicationedDTO>> findAll() {
////        List<publicationedDTO> publications = publicationService.findAll();
////        return ResponseEntity.ok(publications);
////    }
////
////    @PostMapping
////    public ResponseEntity<publicationedDTO> saveOne(@io.swagger.v3.oas.annotations.parameters.RequestBody publicationedDTO publicationedDTO) {
////        publicationedDTO savedPublication = publicationService.saveOne(publicationedDTO);
////        return ResponseEntity.ok(savedPublication);
////    }
////
////    @PostMapping("/addEmployee")
////    public ResponseEntity<String> addEmployeeToPublication(@io.swagger.v3.oas.annotations.parameters.RequestBody publicationedDTO publicationedDTO) {
////        Optional<EmployeeDTO> employeeOptional = employeeService.findOne(publicationedDTO.getEmployeeId());
////        if (employeeOptional.isPresent()) {
////            publicationed publicationed = publicationedMapper.toEntity(publicationedDTO);
////            publicationed.setEmployee(EmployeeMapper.toEntity(employeeOptional.get()));
////            publicationService.saveOne(publicationedMapper.toDTO(publicationed));
////            return ResponseEntity.ok("Employee added to publication.");
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
////
//////    @PostMapping("/addRating")
//////    public ResponseEntity<String> addRatingToPublication(@io.swagger.v3.oas.annotations.parameters.RequestBody publicationedDTO publicationedDTO) {
//////        List<Rating> ratingOptional = ratingService.findRatings(publicationedDTO.getRatingIds());
//////        if (ratingOptional.isEmpty()) {
//////            publicationed publicationed = publicationMapper.topublicationedEntity(publicationedDTO);
//////            publicationed.setRating(ratingOptional);
//////            publicationService.save(publicationed);
//////            return ResponseEntity.ok("Rating added to publication.");
//////        } else {
//////            return ResponseEntity.notFound().build();
//////        }
//////    }
////
//////    @PostMapping("/addEvent")
//////    public ResponseEntity<String> addEventToPublication(@io.swagger.v3.oas.annotations.parameters.RequestBody publicationedDTO publicationedDTO) {
//////        List<Event> events = eventService.findEvents(publicationedDTO.getEventIds());
//////        if (!events.isEmpty()) {
//////            publicationed publicationed = publicationMapper.topublicationedEntity(publicationedDTO);
//////            publicationed.setEvents(events);
//////            publicationService.save(publicationed);
//////            return ResponseEntity.ok().build();
//////        } else {
//////            return ResponseEntity.notFound().build();
//////        }
//////    }
////
////
////    @PutMapping
////    public ResponseEntity<publicationedDTO> update(@RequestBody publicationedDTO publicationedDTO) {
////        publicationedDTO updatedPublication = publicationService.saveOne(publicationedDTO);
////        return ResponseEntity.ok(updatedPublication);
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteOne(@PathVariable Long id) {
////        publicationService.deleteOne(id);
////        return ResponseEntity.noContent().build();
////    }
////}
//
//
//import com.stage.teamb.dtos.PublicationDTO;
//import com.stage.teamb.services.PublicationService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/publications")
//@Slf4j
//public class PublicationController {
//
//    private final PublicationService publicationService;
//
//    @Autowired
//    public PublicationController(PublicationService publicationService) {
//        this.publicationService = publicationService;
//    }
//
//    @GetMapping("/")
//    public ResponseEntity<?> getAllPublications() {
//        try {
//            List<PublicationDTO> publicationedDTOList = publicationService.findAllPublications();
//            return ResponseEntity.ok(publicationedDTOList);
//        } catch (RuntimeException exception) {
//            log.error("Error retrieving publications: " + exception.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error retrieving publications: " + exception.getMessage());
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getPublicationById(@PathVariable Long id) {
//        try {
//            PublicationDTO publicationedDTO = publicationService.findpublicationedById(id);
//            return ResponseEntity.ok(publicationedDTO);
//        } catch (RuntimeException exception) {
//            log.error("Publication not found: " + exception.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Publication not found with id: " + id);
//        }
//    }
//
//    @PostMapping("/")
//    public ResponseEntity<?> createPublication(@RequestBody publicationedDTO publicationedDTO) {
//        try {
//            publicationedDTO createdPublication = publicationService.savepublicationed(publicationedDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdPublication);
//        } catch (RuntimeException exception) {
//            log.error("Could not create publication: " + exception.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Could not create publication: " + exception.getMessage());
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updatePublication(@PathVariable Long id, @RequestBody publicationedDTO publicationedDTO) {
//        try {
//            publicationedDTO.setId(id); // Ensure the ID matches the path variable
//            publicationedDTO updatedPublication = publicationService.updatepublicationed(publicationedDTO);
//            return ResponseEntity.ok(updatedPublication);
//        } catch (RuntimeException exception) {
//            log.error("Could not update publication: " + exception.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Could not update publication: " + exception.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deletePublicationById(@PathVariable Long id) {
//        try {
//            publicationService.deletepublicationedById(id);
//            return ResponseEntity.ok("Publication deleted successfully");
//        } catch (RuntimeException exception) {
//            log.error("Could not delete publication: " + exception.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Could not delete publication: " + exception.getMessage());
//        }
//    }
//
//
//
//}
