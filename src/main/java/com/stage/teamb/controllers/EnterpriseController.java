package com.stage.teamb.controllers;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseCreateDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseManagementDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseUpdateDTO;
import com.stage.teamb.dtos.responsible.ResponsibleDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.services.enterprise.EnterpriseService;
import com.stage.teamb.services.responsible.ResponsibleService;
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
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;
    private final ResponsibleService responsibleService;


    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService, ResponsibleService responsibleService) {
        this.enterpriseService = enterpriseService;
        this.responsibleService = responsibleService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<EnterpriseDTO>> getAllEnterprises() {
        List<EnterpriseDTO> enterprises = enterpriseService.findAllEnterprises();
        return ResponseEntity.ok(enterprises);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EnterpriseDTO> getEnterpriseById(@PathVariable Long id) {
        EnterpriseDTO enterprise = enterpriseService.findEnterpriseById(id);
        return ResponseEntity.ok(enterprise);
    }

    @PostMapping("/create")
    public ResponseEntity<EnterpriseDTO> createEnterprise(@RequestBody EnterpriseCreateDTO enterpriseDTO,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        Optional<ResponsibleDTO> authUser = Optional.ofNullable(responsibleService.findResponsibleByEmail(userDetails.getUsername()));
        if(authUser.isEmpty() || !authUser.get().getId().equals(enterpriseDTO.getResponsibleId())){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        enterpriseDTO.setResponsibleId(authUser.get().getId());
        EnterpriseDTO createdEnterprise = enterpriseService.saveEnterprise(enterpriseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnterprise);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EnterpriseDTO> updateEnterprise(@PathVariable Long id,
                                                          @RequestBody EnterpriseUpdateDTO enterpriseDTO,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        Optional<ResponsibleDTO> authUser = Optional.ofNullable(responsibleService.findResponsibleByEmail(userDetails.getUsername()));
        if(authUser.isEmpty() || !authUser.get().getId().equals(enterpriseDTO.getResponsibleId())){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        enterpriseDTO.setResponsibleId(authUser.get().getId());
        EnterpriseDTO updatedEnterprise = enterpriseService.updateEnterprise(enterpriseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEnterprise);
    }

    @PutMapping("/add-employee")
    public ResponseEntity<EnterpriseDTO> addEmployeeToEnterprise(@RequestBody EnterpriseManagementDTO enterpriseDTO,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Optional<ResponsibleDTO> authUser = Optional.ofNullable(responsibleService.findResponsibleByEmail(userDetails.getUsername()));
        if(authUser.isEmpty() || !authUser.get().getId().equals(enterpriseDTO.getObjectId())){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        EnterpriseDTO updatedEnterprise = enterpriseService.addEmployeeToEnterprise(enterpriseDTO);
        return ResponseEntity.ok(updatedEnterprise);
    }

    @PutMapping("/delete-employee")
    public ResponseEntity<EnterpriseDTO> deleteEmployeeFromEnterprise(@RequestBody EnterpriseManagementDTO enterpriseDTO,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {
        Optional<ResponsibleDTO> authUser = Optional.ofNullable(responsibleService.findResponsibleByEmail(userDetails.getUsername()));
        if(authUser.isEmpty() || !authUser.get().getId().equals(enterpriseDTO.getObjectId())){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        EnterpriseDTO updatedEnterprise = enterpriseService.deleteEmployeeFromEnterprise(enterpriseDTO);
        return ResponseEntity.ok(updatedEnterprise);
    }

    @PutMapping("/add-responsible")
    public ResponseEntity<EnterpriseDTO> addResponsibleToEnterprise(@RequestBody EnterpriseManagementDTO enterpriseDTO,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        Optional<ResponsibleDTO> authUser = Optional.ofNullable(responsibleService.findResponsibleByEmail(userDetails.getUsername()));
        if(authUser.isEmpty() || !authUser.get().getId().equals(enterpriseDTO.getObjectId())){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        EnterpriseDTO updatedEnterprise = enterpriseService.addResponsibleToEnterprise(enterpriseDTO);
        return ResponseEntity.ok(updatedEnterprise);
    }

    @PutMapping("/delete-responsible")
    public ResponseEntity<EnterpriseDTO> deleteResponsibleFromEnterprise(@RequestBody EnterpriseManagementDTO enterpriseDTO,
                                                                         @AuthenticationPrincipal UserDetails userDetails   ) {
        Optional<ResponsibleDTO> authUser = Optional.ofNullable(responsibleService.findResponsibleByEmail(userDetails.getUsername()));
        if(authUser.isEmpty() || !authUser.get().getId().equals(enterpriseDTO.getObjectId())){
            throw new CustomException(403, Collections.singletonList("Forbidden Action"));
        }
        EnterpriseDTO updatedEnterprise = enterpriseService.deleteResponsibleFromEnterprise(enterpriseDTO);
        return ResponseEntity.ok(updatedEnterprise);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable Long id) {
        enterpriseService.deleteEnterpriseById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/{enterpriseId}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@PathVariable Long enterpriseId) {
        List<EmployeeDTO> employees = enterpriseService.getAllEmployees(enterpriseId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/responsibles/{enterpriseId}")
    public ResponseEntity<List<ResponsibleDTO>> getAllResponsibles(@PathVariable Long enterpriseId) {
        List<ResponsibleDTO> responsibles = enterpriseService.getAllResponsibles(enterpriseId);
        return ResponseEntity.ok(responsibles);
    }


//    @GetMapping("/departments/{enterpriseId}")
//    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByEnterpriseId(@PathVariable Long enterpriseId) {
//        List<DepartmentDTO> departments = enterpriseService.findDepartmentsByEnterpriseId(enterpriseId);
//        return ResponseEntity.ok(departments);
//    }
//
//    @PostMapping("/departments/{enterpriseId}")
//    public ResponseEntity<DepartmentDTO> associateDepartmentForEnterprise(
//            @PathVariable Long enterpriseId, @RequestBody DepartmentDTO departmentDTO) {
//        DepartmentDTO createdDepartment = enterpriseService.associateDepartmentWithEnterprise(enterpriseId, departmentDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
//    }
//
//    @DeleteMapping("/departments/{departmentId}")
//    public ResponseEntity<DepartmentDTO> disassociateDepartmentFromEnterprise(@PathVariable Long departmentId) {
//        DepartmentDTO disassociatedDepartment = enterpriseService.disassociateDepartmentFromEnterprise(departmentId);
//        return ResponseEntity.ok(disassociatedDepartment);
//    }
//
//    @GetMapping("/departments/{departmentId}/enterprise")
//    public ResponseEntity<EnterpriseDTO> getEnterpriseByDepartmentId(@PathVariable Long departmentId) {
//        EnterpriseDTO enterprise = enterpriseService.findEnterpriseByDepartmentId(departmentId);
//        return ResponseEntity.ok(enterprise);
//    }


}
