package com.stage.teamb.services;

import com.stage.teamb.dtos.PublishedDTO;
import com.stage.teamb.mappers.PublishedMapper;
import com.stage.teamb.models.Published;
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
    public List<PublishedDTO> findAllPublications() {
        return PublishedMapper.toListDTO(publicationRepository.findAll());
    }

    @Override
    public PublishedDTO findPublishedById(Long id) {
        return PublishedMapper.toDTO(publicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public PublishedDTO savePublished(PublishedDTO PublishedDTO) {
        try {
            return PublishedMapper.toDTO(publicationRepository.save(PublishedMapper.toEntity(PublishedDTO)));
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
    public PublishedDTO updatePublished(PublishedDTO publishedDTO) {
        Published existingPublished= publicationRepository.findById(publishedDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + publishedDTO.getId());
                });
       existingPublished.setNom(publishedDTO.getNom());
       existingPublished.setDescription(publishedDTO.getDescription());
        try {
            return PublishedMapper.toDTO(publicationRepository.save(existingPublished));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }

    @Override
    public List<Published> findAll() {
        return publicationRepository.findAll();
    }

    @Override
    public Optional<Published> findOne(Long id) {
        return publicationRepository.findById(id);
    }

    @Override
    public Published saveOne(Published Published) {
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
