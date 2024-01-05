package com.stage.teamb.controllers;

import com.stage.teamb.dtos.auth.AuthenticationResponse;
import com.stage.teamb.dtos.auth.RegisterRequest;
import com.stage.teamb.dtos.responsible.ResponsibleDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.services.auth.AuthenticationService;
import com.stage.teamb.services.responsible.ResponsibleService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsibles")
@Slf4j
@PreAuthorize("hasRole('RESPONSIBLE')")
public class ResponsibleController {

    private final ResponsibleService responsibleService;
    private final AuthenticationService authenticationService;

    @PreAuthorize("hasRole('RESPONSIBLE')")
    @PostMapping("/register/responsible")
    public ResponseEntity<AuthenticationResponse> registerResponsible(
            @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authenticationService.registerResponsible(request, response));
    }


    @Autowired
    public ResponsibleController(ResponsibleService responsibleService, AuthenticationService authenticationService) {
        this.responsibleService = responsibleService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllResponsibles() {
        try {
            List<ResponsibleDTO> responsibleDTOList = responsibleService.findAllResponsibles();
            return ResponseEntity.ok(responsibleDTOList);
        } catch (CustomException exception) {
            log.error("Error retrieving responsibles: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving responsibles: " + exception.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getResponsibleById(@PathVariable Long id) {
        try {
            ResponsibleDTO responsibleDTO = responsibleService.findResponsibleById(id);
            return ResponseEntity.ok(responsibleDTO);
        } catch (CustomException exception) {
            log.error("Responsible not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Responsible not found with id: " + id);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createResponsible(@RequestBody ResponsibleDTO responsibleDTO) {
        try {
            ResponsibleDTO createdResponsible = responsibleService.saveResponsible(responsibleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResponsible);
        } catch (CustomException exception) {
            log.error("Could not create responsible: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create responsible: " + exception.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateResponsible(@PathVariable Long id, @RequestBody ResponsibleDTO responsibleDTO) {
        try {
            responsibleDTO.setId(id); // Ensure the ID matches the path variable
            ResponsibleDTO updatedResponsible = responsibleService.updateResponsible(responsibleDTO);
            return ResponseEntity.ok(updatedResponsible);
        } catch (CustomException exception) {
            log.error("Could not update responsible: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update responsible: " + exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResponsibleById(@PathVariable Long id) {
        try {
            responsibleService.deleteResponsibleById(id);
            return ResponseEntity.ok("Responsible deleted successfully");
        } catch (CustomException exception) {
            log.error("Could not delete responsible: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete responsible: " + exception.getMessage());
        }
    }
}
