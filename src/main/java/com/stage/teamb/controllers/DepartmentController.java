package com.stage.teamb.controllers;

import com.stage.teamb.dtos.department.DepartmentCreateDTO;
import com.stage.teamb.dtos.department.DepartmentDTO;
import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseDTO;
import com.stage.teamb.services.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.findAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.findDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping("/create")
    public ResponseEntity<DepartmentCreateDTO> createDepartment(@RequestBody DepartmentCreateDTO departmentDTO) {
        DepartmentCreateDTO createdDepartment = departmentService.saveDepartment(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{departmentId}/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartmentId(@PathVariable Long departmentId) {
        List<EmployeeDTO> employees = departmentService.findEmployeesByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/update/{departmentId}/add-employee")
    public ResponseEntity<EmployeeDTO> addEmployeeToDepartment(
            @PathVariable Long departmentId, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO addedEmployee = departmentService.addEmployeeToDepartment(departmentId, employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEmployee);
    }

    @PutMapping("/update/{departmentId}/remove-employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> removeEmployeeFromDepartment(
            @PathVariable Long departmentId, @PathVariable Long employeeId) {
        EmployeeDTO removedEmployee = departmentService.removeEmployeeFromDepartment(departmentId, employeeId);
        return ResponseEntity.ok(removedEmployee);
    }

    @GetMapping("/find/{departmentId}/enterprise")
    public ResponseEntity<EnterpriseDTO> getEnterpriseByDepartmentId(@PathVariable Long departmentId) {
        EnterpriseDTO enterprise = departmentService.getEnterpriseByDepartmentId(departmentId);
        return ResponseEntity.ok(enterprise);
    }

    @PutMapping("/update/{departmentId}/associate-enterprise/{enterpriseId}")
    public ResponseEntity<DepartmentDTO> associateDepartmentWithEnterprise(
            @PathVariable Long departmentId, @PathVariable Long enterpriseId) {
        DepartmentDTO department = departmentService.associateDepartmentWithEnterprise(departmentId, enterpriseId);
        return ResponseEntity.ok(department);
    }

    @PutMapping("/update/{departmentId}/disassociate-enterprise")
    public ResponseEntity<DepartmentDTO> disassociateDepartmentFromEnterprise(@PathVariable Long departmentId) {
        DepartmentDTO department = departmentService.disassociateDepartmentFromEnterprise(departmentId);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/enterprise/{enterpriseId}/departments")
    public ResponseEntity<List<DepartmentDTO>> findDepartmentsByEnterpriseId(@PathVariable Long enterpriseId) {
        List<DepartmentDTO> departments = departmentService.findDepartmentsByEnterpriseId(enterpriseId);
        return ResponseEntity.ok(departments);
    }


}
