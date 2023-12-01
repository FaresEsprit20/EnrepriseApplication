package com.stage.teamb.repository.jpa.responsible;

import com.stage.teamb.models.Responsible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

    @Query("SELECT r FROM Responsible r WHERE r.name = :name OR r.lastName = :lastname")
    List<Responsible> findAllResponsibleByNameOrLastName(@Param("name") String name, @Param("lastname") String lastName);

}
