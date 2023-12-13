package com.stage.teamb.services.rating;


import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.models.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    List<RatingDTO> findRatingsByEmployeeId(Long employeeId);

    List<RatingDTO> findRatingsByPublicationId(Long publicationId);

    RatingDTO createRating(Long publicationId, Long employeeId, Boolean value);

    RatingDTO updateRating(Long ratingId, Boolean value, Long employeeId);

    void deleteRating(Long ratingId, Long employeeId);

    RatingDTO disassociateEmployeeFromRating(Long ratingId);

    RatingDTO upVote(Long publicationId, Long employeeId);

    RatingDTO downVote(Long publicationId, Long employeeId);

    boolean alreadyVoted(Long publicationId, Long employeeId);

    List<Rating> findAll();

    Optional<Rating> findOne(Long id);

    Rating saveOne(Rating rating);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}
