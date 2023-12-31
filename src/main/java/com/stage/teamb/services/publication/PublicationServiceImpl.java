package com.stage.teamb.services.publication;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.publication.PublicationCreateDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.dtos.publication.PublicationGetDTO;
import com.stage.teamb.dtos.rating.RatingCountDTO;
import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.mappers.EmployeeMapper;
import com.stage.teamb.mappers.PublicationMapper;
import com.stage.teamb.mappers.RatingMapper;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Publication;
import com.stage.teamb.models.Rating;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
import com.stage.teamb.repository.jpa.publication.PublicationRepository;
import com.stage.teamb.services.rating.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final RatingService ratingService;
    private final EmployeeRepository employeeRepository;


    @Autowired
    public PublicationServiceImpl(PublicationRepository publicationRepository, RatingService ratingService, EmployeeRepository employeeRepository) {
        this.publicationRepository = publicationRepository;
        this.ratingService = ratingService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<PublicationGetDTO> findAllByEmployeeId(Long employeeId) {
        List<Publication> publications = Optional.ofNullable(publicationRepository.findByEmployeeId(employeeId))
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Publications not found for employee with id " + employeeId)));
        return PublicationMapper.toListGetDTO(publications);
    }

    @Override
    public List<PublicationGetDTO> findAllPublications(Long authUserId) {
        List<Publication> allPublications = publicationRepository.findAll();
        List<PublicationGetDTO> publications = PublicationMapper.toListGetDTO(allPublications);
        publications.forEach(res -> {
            Boolean vote = null;
            boolean isVoting = false;
            // Count upvotes and downvotes separately
            Long upVotesCount = ratingService.countUpVotes(res.getId());
            Long downVotesCount = ratingService.countdownVotes(res.getId());
            RatingCountDTO count = RatingCountDTO.builder()
                    .upVotes(upVotesCount == null ? 0 : upVotesCount)
                    .downVotes(downVotesCount == null ? 0 : downVotesCount)
                    .build();
            Optional<Rating> rating = ratingService.getUserVote(res.getId(), authUserId);
            if (rating.isPresent()) {
                isVoting = true;
                vote = rating.get().getValue();
                res.setVote(vote);
            }else {
                vote = false;
            }
            res.setUpVotes(count.getUpVotes());
            res.setDownVotes(count.getDownVotes());
            res.setUserVoted(isVoting);
            res.setVote(vote);
//            log.warn("Publication ID: {}, Vote: {}, User Voted: {}", res.getId(), vote, isVoting);
        });
        return publications;
    }

    @Override
    public PublicationGetDTO findPublicationById(Long id, Long authUserId) {
        Boolean vote = null;
        boolean isVoting;
        Optional<Rating> rating = ratingService.getUserVote(id, authUserId);
        log.warn(" auth" + authUserId);
        log.warn(" pub" + id);
        log.warn("exists  " + rating.isPresent());
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Not Found ")));
        if (rating.isPresent()) {
            isVoting = true;
            vote = rating.get().getValue();
        } else {
            isVoting = false;
            vote = false;
        }
        // Count upvotes and downvotes separately
        Long upVotesCount = ratingService.countUpVotes(id);
        Long downVotesCount = ratingService.countdownVotes(id);
        RatingCountDTO count = RatingCountDTO.builder()
                .upVotes(upVotesCount == null ? 0 : upVotesCount)
                .downVotes(downVotesCount == null ? 0 : downVotesCount)
                .build();
        log.warn("Vote: " + vote); // Add this log to check the value of vote
        PublicationGetDTO publicationDTO = PublicationMapper.toGetDTO(publication);
        publicationDTO.setVote(vote != null ? vote : false);
        publicationDTO.setUpVotes(count.getUpVotes());
        publicationDTO.setDownVotes(count.getDownVotes());
        publicationDTO.setUserVoted(isVoting);
        return publicationDTO;
    }

    @Override
    public PublicationGetDTO createPublication(PublicationCreateDTO publicationDTO, Long employeeId) {
        // Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Employee not found with id " + employeeId)));
        // Create a new Publication entity and set its values
        Publication newPublication =  Publication.builder()
                .name(publicationDTO.getName())
                .description(publicationDTO.getDescription())
                .employee(employee)
                .build();
        newPublication.setEmployeeForPublication(employee);
        try {
            // Save the new publication to the database
            Publication savedPublication = publicationRepository.save(newPublication);
            // Map the saved publication back to a DTO and return it
            return PublicationMapper.toGetDTO(savedPublication);
        } catch (Exception exception) {
            log.error("Error while creating publication: " + exception.getMessage());
            throw new CustomException(500, Collections.singletonList("Error while creating publication: " + exception.getMessage()));
        }
    }

    @Override
    public PublicationDTO updatePublication(Long publicationId, PublicationDTO publicationDTO) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Publication not found with id " + publicationId)));
        // Update the existing publication with the values from the DTO
        existingPublication.setName(publicationDTO.getName());
        existingPublication.setDescription(publicationDTO.getDescription());
        try {
            return PublicationMapper.toDTO(publicationRepository.save(existingPublication));
        } catch (Exception exception) {
            log.error("Could not update publication: " + exception.getMessage());
            throw new CustomException(500, Collections.singletonList("Could not update publication: " + exception.getMessage()));
        }
    }

    @Override
    public void deletePublication(Long publicationId) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Publication not found with id " + publicationId)));
        // Delete the publication
        publicationRepository.delete(existingPublication);
    }


    @Override
    public EmployeeDTO findEmployeeByPublicationId(Long publicationId) {
        Employee employee = employeeRepository.findEmployeeByPublicationId(publicationId);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public PublicationGetDTO associateEmployeeWithPublication(Long publicationId, Long employeeId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Publication not found with id " + publicationId)));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Employee not found with id " + employeeId)));
        // Associate the publication with the employee
        publication.setEmployeeForPublication(employee);
        try {
            // Save the updated publication to the database using publicationRepository
            Publication savedPublication = publicationRepository.save(publication);
            return PublicationMapper.toGetDTO(savedPublication);
        } catch (Exception exception) {
            log.error("Could not associate employee with publication: " + exception.getMessage());
            throw new CustomException(500, Collections.singletonList("Could not associate employee with publication: " + exception.getMessage()));
        }
    }

    @Override
    public PublicationGetDTO disassociateEmployeeFromPublication(Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Publication not found with id " + publicationId)));
        // Dissociate the publication from the employee
        publication.removeEmployeeForPublication();
        try {
            // Save the updated publication to the database using publicationRepository
            Publication savedPublication = publicationRepository.save(publication);
            return PublicationMapper.toGetDTO(savedPublication);
        } catch (Exception exception) {
            log.error("Could not disassociate employee from publication: " + exception.getMessage());
            throw new CustomException(500, Collections.singletonList("Could not disassociate employee from publication: " + exception.getMessage()));
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
