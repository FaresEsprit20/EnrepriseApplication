package com.stage.teamb.controllers;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.services.publication.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {


    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }


    @GetMapping
    public ResponseEntity<List<PublicationDTO>> getAllPublications() {
        List<PublicationDTO> publications = publicationService.findAllPublications();
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable Long id) {
        PublicationDTO publication = publicationService.findPublicationById(id);
        return ResponseEntity.ok(publication);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PublicationDTO>> getPublicationsByEmployeeId(@PathVariable Long employeeId) {
        List<PublicationDTO> publications = publicationService.findAllByEmployeeId(employeeId);
        return ResponseEntity.ok(publications);
    }

    @PostMapping
    public ResponseEntity<PublicationDTO> createPublication(@RequestBody PublicationDTO publicationDTO) {
        PublicationDTO createdPublication = publicationService.createPublication(publicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPublication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@PathVariable Long id, @RequestBody PublicationDTO publicationDTO) {
        PublicationDTO updatedPublication = publicationService.updatePublication(id, publicationDTO);
        return ResponseEntity.ok(updatedPublication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/employee")
    public EmployeeDTO getEmployeeByPublicationId(@PathVariable Long id) {
        return publicationService.findEmployeeByPublicationId(id);
    }

    @PostMapping("/{id}/employee/{employeeId}")
    public PublicationDTO associateEmployeeWithPublication(@PathVariable Long id, @PathVariable Long employeeId) {
        return publicationService.associateEmployeeWithPublication(id, employeeId);
    }

    @DeleteMapping("/{id}/employee")
    public PublicationDTO disassociateEmployeeFromPublication(@PathVariable Long id) {
        return publicationService.disassociateEmployeeFromPublication(id);
    }



}
