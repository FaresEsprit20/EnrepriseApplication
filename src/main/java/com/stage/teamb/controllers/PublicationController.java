package com.stage.teamb.controllers;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.publication.PublicationCreateDTO;
import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.dtos.publication.PublicationGetDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.services.employee.EmployeeService;
import com.stage.teamb.services.publication.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final EmployeeService employeeService;

    @Autowired
    public PublicationController(PublicationService publicationService, EmployeeService employeeService) {
        this.publicationService = publicationService;
        this.employeeService = employeeService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<PublicationGetDTO>> getAllPublications(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<EmployeeDTO> authUser = Optional.ofNullable(employeeService.findEmployeeByEmail(userDetails.getUsername()));
        if(authUser.isEmpty()){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        List<PublicationGetDTO> publications = publicationService.findAllPublications(authUser.get().getId());
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PublicationGetDTO> getPublicationById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<EmployeeDTO> authUser = Optional.ofNullable(employeeService.findEmployeeByEmail(userDetails.getUsername()));
        if(authUser.isEmpty()){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        PublicationGetDTO publication = publicationService.findPublicationById(id, authUser.get().getId());
        return ResponseEntity.ok(publication);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PublicationGetDTO>> getPublicationsByEmployeeId(@PathVariable Long employeeId) {
        List<PublicationGetDTO> publications = publicationService.findAllByEmployeeId(employeeId);
        return ResponseEntity.ok(publications);
    }

    @PostMapping("/create")
    public ResponseEntity<PublicationGetDTO> createPublication(@RequestBody PublicationCreateDTO publicationDTO) {
        PublicationGetDTO createdPublication = publicationService.createPublication(publicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPublication);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@PathVariable Long id, @RequestBody PublicationDTO publicationDTO) {
        PublicationDTO updatedPublication = publicationService.updatePublication(id, publicationDTO);
        return ResponseEntity.ok(updatedPublication);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/employee")
    public ResponseEntity<EmployeeDTO> getEmployeeByPublicationId(@PathVariable Long id) {
        EmployeeDTO employee = publicationService.findEmployeeByPublicationId(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/{id}/employee/{employeeId}")
    public ResponseEntity<PublicationGetDTO> associateEmployeeWithPublication(@PathVariable Long id, @PathVariable Long employeeId) {
        PublicationGetDTO publication = publicationService.associateEmployeeWithPublication(id, employeeId);
        return ResponseEntity.ok(publication);
    }

    @DeleteMapping("/{id}/employee")
    public ResponseEntity<PublicationGetDTO> disassociateEmployeeFromPublication(@PathVariable Long id) {
        PublicationGetDTO publication = publicationService.disassociateEmployeeFromPublication(id);
        return ResponseEntity.ok(publication);
    }



}
