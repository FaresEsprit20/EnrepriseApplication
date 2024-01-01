package com.stage.teamb.controllers;

import com.stage.teamb.dtos.department.DepartmentDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseDTO;
import com.stage.teamb.services.enterprise.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
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
    public ResponseEntity<EnterpriseDTO> createEnterprise(@RequestBody EnterpriseDTO enterpriseDTO) {
        EnterpriseDTO createdEnterprise = enterpriseService.saveEnterprise(enterpriseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnterprise);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EnterpriseDTO> updateEnterprise(@PathVariable Long id, @RequestBody EnterpriseDTO enterpriseDTO) {
        enterpriseDTO.setId(id);
        EnterpriseDTO updatedEnterprise = enterpriseService.updateEnterprise(enterpriseDTO);
        return ResponseEntity.ok(updatedEnterprise);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable Long id) {
        enterpriseService.deleteEnterpriseById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/departments/{enterpriseId}")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByEnterpriseId(@PathVariable Long enterpriseId) {
        List<DepartmentDTO> departments = enterpriseService.findDepartmentsByEnterpriseId(enterpriseId);
        return ResponseEntity.ok(departments);
    }

    @PostMapping("/departments/{enterpriseId}")
    public ResponseEntity<DepartmentDTO> associateDepartmentForEnterprise(
            @PathVariable Long enterpriseId, @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createdDepartment = enterpriseService.associateDepartmentWithEnterprise(enterpriseId, departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @DeleteMapping("/departments/{departmentId}")
    public ResponseEntity<DepartmentDTO> disassociateDepartmentFromEnterprise(@PathVariable Long departmentId) {
        DepartmentDTO disassociatedDepartment = enterpriseService.disassociateDepartmentFromEnterprise(departmentId);
        return ResponseEntity.ok(disassociatedDepartment);
    }

    @GetMapping("/departments/{departmentId}/enterprise")
    public ResponseEntity<EnterpriseDTO> getEnterpriseByDepartmentId(@PathVariable Long departmentId) {
        EnterpriseDTO enterprise = enterpriseService.findEnterpriseByDepartmentId(departmentId);
        return ResponseEntity.ok(enterprise);
    }

    
}
