package com.stage.teamb.repository.jpa.rating;

import com.stage.teamb.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {


    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId")
    List<Rating> findRatingsByPublicationId(@Param("publicationId") Long publicationId);

    @Query("SELECT r FROM Rating r WHERE r.employee.id = :employeeId")
    List<Rating> findRatingsByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.publication WHERE r.id = :ratingId")
    Rating findByIdWithPublication(Long ratingId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.employee WHERE r.id = :ratingId")
    Rating findByIdWithEmployee(Long ratingId);

    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId AND r.employee.id = :employeeId")
    Optional<Rating> findByPublicationAndEmployee(@Param("publicationId") Long publicationId, @Param("employeeId") Long employeeId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.publication LEFT JOIN FETCH r.employee WHERE r.id = :ratingId")
    Rating findByIdEagerly(Long ratingId);

    @Query(value = "SELECT COUNT(*) FROM Rating r WHERE r.publication.id = :publicationId AND r.value = true")
    Long countUpVotesByPublicationId(@Param("publicationId") Long publicationId);

    @Query(value = "SELECT COUNT(*) FROM Rating r WHERE r.publication.id = :publicationId AND r.value = false")
    Long countDownVotesByPublicationId(@Param("publicationId") Long publicationId);



}

