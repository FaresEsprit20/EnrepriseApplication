package com.stage.teamb.services;

import com.stage.teamb.dtos.PublicationDTO;
import com.stage.teamb.mappers.PublicationMapper;
import com.stage.teamb.models.Publication;
import com.stage.teamb.repository.PublicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    @Autowired
    public PublicationServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public List<PublicationDTO> findAllPublications() {
        return PublicationMapper.toListDTO(publicationRepository.findAll());
    }

    @Override
    public PublicationDTO findPublishedById(Long id) {
        return PublicationMapper.toDTO(publicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public PublicationDTO savePublished(PublicationDTO PublishedDTO) {
        try {
            return PublicationMapper.toDTO(publicationRepository.save(PublicationMapper.toEntity(PublishedDTO)));
        }catch (Exception exception){
            log.error("Address with not found.");
            throw new RuntimeException("Can not save this entity  :   "+exception.getMessage());
        }
    }

    @Override
    public void deletePublishedById(Long id) {
        if (publicationRepository.existsById(id)) {
            try{
                publicationRepository.deleteById(id);
            }catch (Exception exception) {
                log.error("Can not delete this entity"+exception.getMessage());
                throw new RuntimeException("Can not delete this entity  :   "+exception.getMessage());
            }
        } else {
            log.error("Entity Not Exist");
            throw new RuntimeException("Entity Not Exist");
        }
    }

    @Override
    public PublicationDTO updatePublished(PublicationDTO publishedDTO) {
        Publication existingPublished= publicationRepository.findById(publishedDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + publishedDTO.getId());
                });
       existingPublished.setNom(publishedDTO.getNom());
       existingPublished.setDescription(publishedDTO.getDescription());
        try {
            return PublicationMapper.toDTO(publicationRepository.save(existingPublished));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }

    @Override
    public List<Publication> findAll() {
        return publicationRepository.findAll();
    }

    @Override
    public Optional<Publication> findOne(Long id) {
        return publicationRepository.findById(id);
    }

    @Override
    public Publication saveOne(Publication Published) {
        return publicationRepository.save(Published);
    }

    @Override
    public void deleteOne(Long id) {
        publicationRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return publicationRepository.existsById(id);
    }



}
