package com.stage.teamb.services;


import com.stage.teamb.dtos.RatingDTO;
import com.stage.teamb.mappers.RatingMapper;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Publication;
import com.stage.teamb.models.Rating;
import com.stage.teamb.repository.EmployeeRepository;
import com.stage.teamb.repository.PublicationRepository;
import com.stage.teamb.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final PublicationRepository publishedRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, PublicationRepository publishedRepository, EmployeeRepository employeeRepository) {
        this.ratingRepository = ratingRepository;
        this.publishedRepository = publishedRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<RatingDTO> findRatingsByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        List<Rating> ratings = employee.getRatings();
        return RatingMapper.toListDTO(ratings);
    }

    @Override
    public List<RatingDTO> findRatingsByPublicationId(Long publicationId) {
        Publication publication = publishedRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        List<Rating> ratings = publication.getRating();
        return RatingMapper.toListDTO(ratings);
    }

    @Override
    public RatingDTO createRating(Long publicationId, Long employeeId, Boolean value) {
        Publication publication = publishedRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        if (employee.getRatings().stream().anyMatch(rating -> rating.getPublication().getId().equals(publicationId))) {
            throw new RuntimeException("Employee has already rated this publication.");
        }
        Rating rating = new Rating();
        rating.setPublication(publication);
        rating.setEmployee(employee);
        rating.setValue(value);
        return RatingMapper.toDTO(ratingRepository.save(rating));
    }

    @Override
    public RatingDTO updateRating(Long ratingId, Boolean value, Long employeeId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        if (!rating.getEmployee().getId().equals(employeeId)) {
            throw new IllegalArgumentException("You can only update your own ratings.");
        }
        rating.setValue(value);
        return RatingMapper.toDTO(ratingRepository.save(rating));
    }

    @Override
    public void deleteRating(Long ratingId, Long employeeId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        if (!rating.getEmployee().getId().equals(employeeId)) {
            throw new IllegalArgumentException("You can only delete your own ratings.");
        }
        ratingRepository.deleteById(ratingId);
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
