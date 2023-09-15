package com.stage.teamb.repository;


import com.stage.teamb.models.Published;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Published, Long> {


}
