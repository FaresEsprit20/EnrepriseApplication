package com.stage.teamb.services;

import com.stage.teamb.dtos.PublishedDTO;
import com.stage.teamb.models.Published;

import java.util.List;
import java.util.Optional;

public interface PublicationService {


    List<PublishedDTO> findAllPublications();

    PublishedDTO findPublishedById(Long id);

    PublishedDTO savePublished(PublishedDTO PublishedDTO);

    void deletePublishedById(Long id);

    PublishedDTO updatePublished(PublishedDTO publishedDTO);

    List<Published> findAll();

    Optional<Published> findOne(Long id);

    Published saveOne(Published Published);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}