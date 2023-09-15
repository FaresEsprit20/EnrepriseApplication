package com.stage.teamb.controllers;//package com.stage.teamb.controllers;
//import com.stage.teamb.dtos.EmployeeDTO;
//import com.stage.teamb.mappers.EmployeeMapper;
//import com.stage.teamb.mappers.RatingMapper;
//import com.stage.teamb.models.*;
//import com.stage.teamb.dtos.RatingDTO;
//import com.stage.teamb.services.EmployeeService;
//import com.stage.teamb.services.PublicationService;
//import com.stage.teamb.services.RatingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/ratings")
//public class RatingController {
//    private final RatingService ratingService;
//    private final EmployeeService employeeService;
//    private final PublicationService publicationService;
//
//
//    @Autowired
//    public RatingController(RatingService ratingService, EmployeeService employeeService, PublicationService publicationService) {
//        this.ratingService = ratingService;
//        this.employeeService = employeeService;
//        this.publicationService = publicationService;
//    }
//
//
//    @GetMapping("/{id}")
//    public Optional<RatingDTO> findOne(@PathVariable Long id) {
//        return ratingService.findOne(id);
//    }
//
//    @GetMapping
//    public List<RatingDTO> findAll() {
//        return ratingService.findAll();
//    }
//    @PostMapping
//    public RatingDTO saveOne(@RequestBody RatingDTO rating) {
//        return ratingService.saveOne(rating);
//    }
//    @PostMapping("/addEmployeeToRating")
//    public ResponseEntity<String> addEmployeeToRating(@RequestBody RatingDTO ratingDto) {
//        Optional<EmployeeDTO> employeeOptional = employeeService.findOne(ratingDto.getEmployeeId());
//        if (employeeOptional.isPresent()) {
//            Rating rating = RatingMapper.toEntity(ratingDto);
//            rating.setEmployee(EmployeeMapper.toEntity(employeeOptional.get()));
//            ratingService.saveOne(RatingMapper.toDTO(rating));
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    @PostMapping("/addPublishedToRating")
//    public ResponseEntity<String> addPublishedToRating(@RequestBody RatingDTO ratingDto) {
//        List<Published> publisheds = publicationService.findPublishedList(ratingDto.getPublishedsId());
//
//        if (!publisheds.isEmpty()) {
//            Rating rating = RatingMapper.toEntity(ratingDto);
//            rating.setPublished((Published) publisheds);
//            ratingService.saveOne(RatingMapper.toDTO(rating));
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//    @PutMapping("/update")
//    public RatingDTO update(@RequestBody RatingDTO rating) {
//        return ratingService.update(rating);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteOne(@PathVariable Long id) {
//        ratingService.deleteOne(id);
//    }
//}
//


import com.stage.teamb.dtos.RatingDTO;
import com.stage.teamb.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Slf4j
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllRatings() {
        try {
            List<RatingDTO> ratingDTOList = ratingService.findAllRatings();
            return ResponseEntity.ok(ratingDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving ratings: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving ratings: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRatingById(@PathVariable Long id) {
        try {
            RatingDTO ratingDTO = ratingService.findRatingById(id);
            return ResponseEntity.ok(ratingDTO);
        } catch (RuntimeException exception) {
            log.error("Rating not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rating not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createRating(@RequestBody RatingDTO ratingDTO) {
        try {
            RatingDTO createdRating = ratingService.saveRating(ratingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
        } catch (RuntimeException exception) {
            log.error("Could not create rating: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create rating: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        try {
            ratingDTO.setId(id); // Ensure the ID matches the path variable
            RatingDTO updatedRating = ratingService.updateRating(ratingDTO);
            return ResponseEntity.ok(updatedRating);
        } catch (RuntimeException exception) {
            log.error("Could not update rating: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update rating: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRatingById(@PathVariable Long id) {
        try {
            ratingService.deleteRatingById(id);
            return ResponseEntity.ok("Rating deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete rating: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete rating: " + exception.getMessage());
        }
    }


}

