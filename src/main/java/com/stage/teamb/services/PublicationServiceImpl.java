package com.stage.teamb.services;

import com.stage.teamb.dtos.PublicationDTO;
import com.stage.teamb.mappers.PublicationMapper;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Publication;
import com.stage.teamb.repository.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;

    @Autowired
    public PublicationServiceImpl(PublicationRepository publicationRepository, EmployeeRepository employeeRepository) {
        this.publicationRepository = publicationRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<PublicationDTO> findAllPublications() {
        return PublicationMapper.toListDTO(publicationRepository.findAll());
    }

    @Override
    public PublicationDTO findPublicationById(Long id) {
        return PublicationMapper.toDTO(publicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public List<PublicationDTO> findAllByEmployeeId(Long employeeId) {
        List<Publication> publications = Optional.ofNullable(publicationRepository.findByEmployeeId(employeeId))
                .orElseThrow(() -> new RuntimeException("Publications not found for employee with id " + employeeId));
        return PublicationMapper.toListDTO(publications);
    }


    @Override
    public PublicationDTO savePublication(Long employeeId, PublicationDTO publicationDTO) {
        // Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        // Create a new Publication entity and set its values
        Publication newPublication = PublicationMapper.toEntity(publicationDTO);
        newPublication.setEmployee(employee);
        try {
            // Save the new publication to the database
            Publication savedPublication = publicationRepository.save(newPublication);
            // Map the saved publication back to a DTO and return it
            return PublicationMapper.toDTO(savedPublication);
        } catch (Exception exception) {
            log.error("Error while creating publication: " + exception.getMessage());
            throw new RuntimeException("Error while creating publication: " + exception.getMessage());
        }
    }


    @Override
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        try {
            return PublicationMapper.toDTO(publicationRepository.save(PublicationMapper.toEntity(publicationDTO)));
        } catch (Exception exception) {
            log.error("Error while creating publication: " + exception.getMessage());
            throw new RuntimeException("Error while creating publication: " + exception.getMessage());
        }
    }

    @Override
    public PublicationDTO updatePublication(Long publicationId, PublicationDTO publicationDTO) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));

        // Update the existing publication with the values from the DTO
        existingPublication.setNom(publicationDTO.getNom());
        existingPublication.setDescription(publicationDTO.getDescription());

        try {
            return PublicationMapper.toDTO(publicationRepository.save(existingPublication));
        } catch (Exception exception) {
            log.error("Could not update publication: " + exception.getMessage());
            throw new RuntimeException("Could not update publication: " + exception.getMessage());
        }
    }

    @Override
    public void deletePublication(Long publicationId) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));

        // Delete the publication
        publicationRepository.delete(existingPublication);
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
