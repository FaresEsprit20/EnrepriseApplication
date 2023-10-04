package com.stage.teamb.services.publication;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.event.EventDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.dtos.rating.RatingDTO;
import com.stage.teamb.mappers.EmployeeMapper;
import com.stage.teamb.mappers.EventMapper;
import com.stage.teamb.mappers.PublicationMapper;
import com.stage.teamb.mappers.RatingMapper;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Event;
import com.stage.teamb.models.Publication;
import com.stage.teamb.models.Rating;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
import com.stage.teamb.repository.jpa.event.EventRepository;
import com.stage.teamb.repository.jpa.publication.PublicationRepository;
import com.stage.teamb.repository.jpa.rating.RatingRepository;
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
    private final EventRepository eventRepository;
    private final RatingRepository ratingRepository;


    @Autowired
    public PublicationServiceImpl(PublicationRepository publicationRepository, EmployeeRepository employeeRepository, EventRepository eventRepository, RatingRepository ratingRepository) {
        this.publicationRepository = publicationRepository;
        this.employeeRepository = employeeRepository;
        this.eventRepository = eventRepository;
        this.ratingRepository = ratingRepository;
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
    public List<EventDTO> findEventsByPublicationId(Long publicationId) {
        List<Event> events = eventRepository.findAllByPublicationId(publicationId);
        return EventMapper.toListDTO(events);
    }



    @Override
    public EventDTO addEventToPublication(Long publicationId, EventDTO eventDTO) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        Event event = EventMapper.toEntity(eventDTO); // Use your EventMapper to convert EventDTO to Event
        event.setPublicationForEvent(publication);
        Event savedEvent = eventRepository.save(event);
        return EventMapper.toDTO(savedEvent); // Use your EventMapper to convert Event to EventDTO
    }


    @Override
    public void removeEventFromPublication(Long publicationId, Long eventId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
        if (!publication.getEvents().contains(event)) {
            log.error("Event is not associated with the publication.");
            throw new RuntimeException("Event is not associated with the publication.");
        }
        event.removePublicationForEvent();
        eventRepository.save(event);
    }

    @Override
    public PublicationDTO findPublicationByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
        Publication publication = event.getPublication();
        if (publication == null) {
            throw new RuntimeException("No publication associated with event " + eventId);
        }
        return PublicationMapper.toDTO(publication);
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
    public RatingDTO createRating(RatingDTO ratingDTO) {
        // Find the publication by ID
        Publication publication = publicationRepository.findById(ratingDTO.getPublicationId())
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + ratingDTO.getPublicationId()));
        // Find the employee by ID
        Employee employee = employeeRepository.findById(ratingDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + ratingDTO.getEmployeeId()));
        // Create a new Rating entity and set its values
        Rating newRating = RatingMapper.toEntity(ratingDTO);
        newRating.setPublicationForRating(publication);
        newRating.setEmployeeForRating(employee);
        try {
            // Save the new rating to the database
            Rating savedRating = ratingRepository.save(newRating);
            // Map the saved rating back to a DTO and return it
            return RatingMapper.toDTO(savedRating);
        } catch (Exception exception) {
            log.error("Error while creating rating: " + exception.getMessage());
            throw new RuntimeException("Error while creating rating: " + exception.getMessage());
        }
    }

    @Override
    public RatingDTO updateRating(Long ratingId, RatingDTO ratingDTO) {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        // Update the existing rating with the values from the DTO
        existingRating.setValue(ratingDTO.getValue());
        try {
            // Save the updated rating to the database
            Rating savedRating = ratingRepository.save(existingRating);
            // Map the saved rating back to a DTO and return it
            return RatingMapper.toDTO(savedRating);
        } catch (Exception exception) {
            log.error("Could not update rating: " + exception.getMessage());
            throw new RuntimeException("Could not update rating: " + exception.getMessage());
        }
    }

    @Override
    public void deleteRating(Long ratingId) {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        // Delete the rating
        ratingRepository.delete(existingRating);
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
