package com.stage.teamb.repository.jpa.publication;


import com.stage.teamb.models.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT p FROM Publication p JOIN FETCH p.employee e WHERE e.id = :employeeId")
    List<Publication> findByEmployeeId(Long employeeId);

    @Query("SELECT p FROM Publication p LEFT JOIN FETCH p.employee WHERE p.id = :publicationId")
    Publication findByIdWithEmployee(Long publicationId);

    @Query("SELECT p FROM Publication p LEFT JOIN FETCH p.events WHERE p.id = :publicationId")
    Publication findByIdWithEvents(Long publicationId);

    @Query("SELECT p FROM Publication p LEFT JOIN FETCH p.rating WHERE p.id = :publicationId")
    Publication findByIdWithRating(Long publicationId);




}
