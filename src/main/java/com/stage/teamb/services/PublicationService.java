package com.stage.teamb.services;

import com.stage.teamb.dtos.PublicationDTO;
import com.stage.teamb.models.Publication;

import java.util.List;
import java.util.Optional;

public interface PublicationService {


    List<PublicationDTO> findAllPublications();

    PublicationDTO findPublicationById(Long id);

    List<PublicationDTO> findAllByEmployeeId(Long employeeId);

    PublicationDTO savePublication(Long employeeId, PublicationDTO publicationDTO);

    PublicationDTO createPublication(PublicationDTO publicationDTO);

    PublicationDTO updatePublication(Long publicationId, PublicationDTO publicationDTO);

    void deletePublication(Long publicationId);

    List<Publication> findAll();

    Optional<Publication> findOne(Long id);

    Publication saveOne(Publication Published);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}