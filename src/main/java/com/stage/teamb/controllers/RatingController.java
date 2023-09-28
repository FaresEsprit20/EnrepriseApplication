package com.stage.teamb.controllers;

import com.stage.teamb.dtos.RatingDTO;
import com.stage.teamb.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<RatingDTO>> findRatingsByEmployeeId(@PathVariable Long employeeId) {
        List<RatingDTO> ratings = ratingService.findRatingsByEmployeeId(employeeId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<List<RatingDTO>> findRatingsByPublicationId(@PathVariable Long publicationId) {
        List<RatingDTO> ratings = ratingService.findRatingsByPublicationId(publicationId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO) {
        RatingDTO rating = ratingService.createRating(ratingDTO.getPublicationId(),ratingDTO.getEmployeeId(),ratingDTO.getValue());
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable Long ratingId, @RequestBody RatingDTO ratingDTO) {
        RatingDTO updatedRating = ratingService.updateRating(ratingId, ratingDTO.getValue(), ratingDTO.getEmployeeId());
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId,
                                             @RequestParam Long employeeId) {
        ratingService.deleteRating(ratingId, employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}
