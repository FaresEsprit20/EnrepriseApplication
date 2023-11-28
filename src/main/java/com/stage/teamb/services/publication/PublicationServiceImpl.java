package com.stage.teamb.services.publication;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.mappers.EmployeeMapper;
import com.stage.teamb.mappers.PublicationMapper;
import com.stage.teamb.mappers.RatingMapper;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Publication;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
import com.stage.teamb.repository.jpa.publication.PublicationRepository;
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
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        Long employeeId = publicationDTO.getEmployeeId(); // Get employeeId from the DTO
        // Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        // Create a new Publication entity and set its values
        Publication newPublication = PublicationMapper.toEntity(publicationDTO);
        newPublication.setEmployeeForPublication(employee);
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
    public PublicationDTO updatePublication(Long publicationId, PublicationDTO publicationDTO) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        // Update the existing publication with the values from the DTO
        existingPublication.setName(publicationDTO.getName());
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
    public EmployeeDTO findEmployeeByPublicationId(Long publicationId) {
        Employee employee = employeeRepository.findEmployeeByPublicationId(publicationId);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public PublicationDTO associateEmployeeWithPublication(Long publicationId, Long employeeId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        // Associate the publication with the employee
        publication.setEmployeeForPublication(employee);
        try {
            // Save the updated publication to the database using publicationRepository
            Publication savedPublication = publicationRepository.save(publication);
            return PublicationMapper.toDTO(savedPublication);
        } catch (Exception exception) {
            log.error("Could not associate employee with publication: " + exception.getMessage());
            throw new RuntimeException("Could not associate employee with publication: " + exception.getMessage());
        }
    }


    @Override
    public PublicationDTO disassociateEmployeeFromPublication(Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        // Dissociate the publication from the employee
        publication.removeEmployeeForPublication();
        try {
            // Save the updated publication to the database using publicationRepository
            Publication savedPublication = publicationRepository.save(publication);
            return PublicationMapper.toDTO(savedPublication);
        } catch (Exception exception) {
            log.error("Could not disassociate employee from publication: " + exception.getMessage());
            throw new RuntimeException("Could not disassociate employee from publication: " + exception.getMessage());
        }
    }

    @Override
    public List<RatingDTO> getRatingForPublication(Long publicationId) {
        return RatingMapper.toListDTO(publicationRepository.findByIdWithRating(publicationId).getRating());
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
