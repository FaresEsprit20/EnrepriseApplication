package com.stage.teamb.repository;


import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Event;
import com.stage.teamb.models.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findByEmployeeId(Long employeeId);

    @Query("SELECT e FROM Event e WHERE e.publication.id = :publicationId")
    List<Event> findEventsByPublicationId(@Param("publicationId") Long publicationId);

    @Query("SELECT p.employee FROM Publication p WHERE p.id = :publicationId")
    Employee findEmployeeByPublicationId(@Param("publicationId") Long publicationId);


}
