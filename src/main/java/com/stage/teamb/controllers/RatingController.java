package com.stage.teamb.controllers;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.rating.RatingCountDTO;
import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.services.employee.EmployeeService;
import com.stage.teamb.services.rating.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final EmployeeService employeeService;

    @Autowired
    public RatingController(RatingService ratingService, EmployeeService employeeService) {
        this.ratingService = ratingService;
        this.employeeService = employeeService;
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

    @GetMapping("/publication/{publicationId}/votes/count")
    public ResponseEntity<RatingCountDTO> findVotesByPublicationId(@PathVariable Long publicationId) {
           Long upVotes = ratingService.countUpVotes(publicationId);
           Long downVotes = ratingService.countUpVotes(publicationId);
          RatingCountDTO count = RatingCountDTO.builder()
                   .upVotes(upVotes)
                   .downVotes(downVotes)
                   .build();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO) {
        RatingDTO rating = ratingService.createRating(ratingDTO.getPublicationId(), ratingDTO.getEmployeeId(), ratingDTO.getValue());
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable Long ratingId, @RequestBody RatingDTO ratingDTO) {
        RatingDTO updatedRating = ratingService.updateRating(ratingId, ratingDTO.getValue(), ratingDTO.getEmployeeId());
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId, @RequestParam Long employeeId) {
        ratingService.deleteRating(ratingId, employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upvote/{publicationId}")
    public ResponseEntity<RatingDTO> upVote(@PathVariable Long publicationId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Optional<EmployeeDTO> authUser = Optional.ofNullable(employeeService.findEmployeeByEmail(userDetails.getUsername()));
            if(authUser.isEmpty()){
                throw new CustomException(403, Collections.singletonList("Forbidden Action"));
            }
            RatingDTO ratingDTO = ratingService.upVote(publicationId, authUser.get().getId());
            return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception and return appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/downvote/{publicationId}")
    public ResponseEntity<RatingDTO> downVote(@PathVariable Long publicationId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Optional<EmployeeDTO> authUser = Optional.ofNullable(employeeService.findEmployeeByEmail(userDetails.getUsername()));
            if(authUser.isEmpty()){
                throw new CustomException(403, Collections.singletonList("Forbidden Action"));
            }
            RatingDTO ratingDTO = ratingService.downVote(publicationId, authUser.get().getId());
            return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception and return appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
