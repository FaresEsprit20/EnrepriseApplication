package com.stage.teamb.services;

import com.stage.teamb.dtos.PublicationDTO;
import com.stage.teamb.models.Publication;

import java.util.List;
import java.util.Optional;

public interface PublicationService {


    List<PublicationDTO> findAllPublications();

    PublicationDTO findPublishedById(Long id);

    PublicationDTO savePublished(PublicationDTO PublishedDTO);

    void deletePublishedById(Long id);

    PublicationDTO updatePublished(PublicationDTO publishedDTO);

    List<Publication> findAll();

    Optional<Publication> findOne(Long id);

    Publication saveOne(Publication Published);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}