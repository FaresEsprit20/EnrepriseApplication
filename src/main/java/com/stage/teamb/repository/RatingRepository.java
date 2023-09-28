package com.stage.teamb.repository;

import com.stage.teamb.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {


    @Query("SELECT r FROM Rating r WHERE r.publication.id = :publicationId")
    List<Rating> findRatingsByPublicationId(@Param("publicationId") Long publicationId);

    @Query("SELECT r FROM Rating r WHERE r.employee.id = :employeeId")
    List<Rating> findRatingsByEmployeeId(@Param("employeeId") Long employeeId);

}

