package com.stage.teamb.services;

import com.stage.teamb.dtos.RatingDTO;
import com.stage.teamb.models.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {


    List<RatingDTO> findAllRatings();

    RatingDTO findRatingById(Long id);

    RatingDTO saveRating(RatingDTO ratingDTO);

    void deleteRatingById(Long id);

    RatingDTO updateRating(RatingDTO ratingDTO);

    List<Rating> findAll();

    Optional<Rating> findOne(Long id);

    Rating saveOne(Rating Rating);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}