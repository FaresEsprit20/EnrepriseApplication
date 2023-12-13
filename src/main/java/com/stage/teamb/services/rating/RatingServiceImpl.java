package com.stage.teamb.services.rating;


import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.mappers.RatingMapper;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Publication;
import com.stage.teamb.models.Rating;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
import com.stage.teamb.repository.jpa.publication.PublicationRepository;
import com.stage.teamb.repository.jpa.rating.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final PublicationRepository publicationRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, PublicationRepository publicationRepository, EmployeeRepository employeeRepository) {
        this.ratingRepository = ratingRepository;
        this.publicationRepository = publicationRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<RatingDTO> findRatingsByEmployeeId(Long employeeId) {
        return RatingMapper.toListDTO(ratingRepository.findRatingsByEmployeeId(employeeId));
    }

    @Override
    public List<RatingDTO> findRatingsByPublicationId(Long publicationId) {
        return RatingMapper.toListDTO(ratingRepository.findRatingsByPublicationId(publicationId));
    }

    @Override
    public RatingDTO createRating(Long publicationId, Long employeeId, Boolean value) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        if (employee.getRatings().stream().anyMatch(rating -> rating.getPublication().getId().equals(publicationId))) {
            throw new RuntimeException("Employee has already rated this publication.");
        }
        Rating rating = new Rating();
        rating.setPublicationForRating(publication);
        rating.setEmployeeForRating(employee);
        rating.setValue(value);
        return RatingMapper.toDTO(ratingRepository.save(rating));
    }

    @Override
    public RatingDTO updateRating(Long ratingId, Boolean value, Long employeeId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        if (!rating.getEmployee().getId().equals(employeeId)) {
            throw new RuntimeException("You can only update your own ratings.");
        }
        rating.setValue(value);
        return RatingMapper.toDTO(ratingRepository.save(rating));
    }

    @Override
    public void deleteRating(Long ratingId, Long employeeId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        if (!rating.getEmployee().getId().equals(employeeId)) {
            throw new RuntimeException("You can only delete your own ratings.");
        }
        ratingRepository.deleteById(ratingId);
    }




    @Override
    public RatingDTO disassociateEmployeeFromRating(Long ratingId) {
        try {
            Rating rating = ratingRepository.findById(ratingId)
                    .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
            // Use the helper method to disassociate the employee from the rating
            rating.removeEmployeeFromRating();
            // Save the updated rating
            Rating updatedRating = ratingRepository.save(rating);
            return RatingMapper.toDTO(updatedRating);
        } catch (Exception exception) {
            log.error("Could not disassociate employee from rating: " + exception.getMessage());
            throw new RuntimeException("Could not disassociate employee from rating: " + exception.getMessage());
        }
    }


    @Override
    public RatingDTO upVote(Long publicationId, Long employeeId) {
        return vote(publicationId, employeeId, true);
    }

    @Override
    public RatingDTO downVote(Long publicationId, Long employeeId) {
        return vote(publicationId, employeeId, false);
    }

    @Override
    public boolean alreadyVoted(Long publicationId, Long employeeId) {
        Optional<Rating> existingRating = ratingRepository.findByPublicationIdAndEmployeeId(publicationId, employeeId);
        return existingRating.isPresent();
    }

    @Override
    public Optional<Rating> getUserVote(Long publicationId, Long employeeId) {
        return ratingRepository.findByPublicationIdAndEmployeeId(publicationId, employeeId);
    }


    private RatingDTO vote(Long publicationId, Long employeeId, Boolean value) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));

        Optional<Rating> existingRating = ratingRepository.findByPublicationIdAndEmployeeId(publicationId, employeeId);

        if (existingRating.isPresent()) {
            return  this.updateRating(existingRating.get().getId(), value, employeeId);
        } else {
            return this.createRating(publicationId, employeeId, value);
        }
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> findOne(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public Rating saveOne(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteOne(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return ratingRepository.existsById(id);
    }


}
