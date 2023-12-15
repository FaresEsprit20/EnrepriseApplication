package com.stage.teamb.services.publication;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.publication.PublicationCreateDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.dtos.publication.PublicationGetDTO;
import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.models.Publication;

import java.util.List;
import java.util.Optional;

public interface PublicationService {


    List<PublicationGetDTO> findAllPublications(Long authId);

    PublicationGetDTO findPublicationById(Long id, Long authUserId);

    List<PublicationGetDTO> findAllByEmployeeId(Long employeeId);

    PublicationGetDTO createPublication(PublicationCreateDTO publicationDTO, Long id);

    PublicationDTO updatePublication(Long publicationId, PublicationDTO publicationDTO);

    void deletePublication(Long publicationId);

    EmployeeDTO findEmployeeByPublicationId(Long publicationId);

    PublicationGetDTO associateEmployeeWithPublication(Long publicationId, Long employeeId);

    PublicationGetDTO disassociateEmployeeFromPublication(Long publicationId);


    List<RatingDTO> getRatingForPublication(Long publicationId);

    List<Publication> findAll();

    Optional<Publication> findOne(Long id);

    Publication saveOne(Publication Published);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}