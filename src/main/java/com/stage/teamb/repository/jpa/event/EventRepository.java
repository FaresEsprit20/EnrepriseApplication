package com.stage.teamb.repository.jpa.event;

import com.stage.teamb.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {


    @Query("SELECT e FROM Event e JOIN FETCH e.publication p WHERE p.id = :publicationId")
    List<Event> findAllByPublicationId(Long publicationId);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.publication WHERE e.id = :eventId")
    Event findByIdWithPublication(Long eventId);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.responsible WHERE e.id = :eventId")
    Event findByIdWithResponsible(Long eventId);

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.publication LEFT JOIN FETCH e.responsible WHERE e.id = :eventId")
    Event findByIdEagerly(Long eventId);



}
