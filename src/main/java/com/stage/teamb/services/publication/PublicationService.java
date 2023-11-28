package com.stage.teamb.services.publication;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.models.Publication;

import java.util.List;
import java.util.Optional;

public interface PublicationService {


    List<PublicationDTO> findAllPublications();

    PublicationDTO findPublicationById(Long id);

    List<PublicationDTO> findAllByEmployeeId(Long employeeId);

    PublicationDTO createPublication(PublicationDTO publicationDTO);

    PublicationDTO updatePublication(Long publicationId, PublicationDTO publicationDTO);

    void deletePublication(Long publicationId);

    EmployeeDTO findEmployeeByPublicationId(Long publicationId);

    PublicationDTO associateEmployeeWithPublication(Long publicationId, Long employeeId);

    PublicationDTO disassociateEmployeeFromPublication(Long publicationId);


    List<RatingDTO> getRatingForPublication(Long publicationId);

    List<Publication> findAll();

    Optional<Publication> findOne(Long id);

    Publication saveOne(Publication Published);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}