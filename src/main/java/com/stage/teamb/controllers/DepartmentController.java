package com.stage.teamb.controllers;//package com.stage.teamb.controllers;
//
//
//import com.stage.teamb.dtos.DepartmentDTO;
//import com.stage.teamb.services.DepartmentService;
//import com.stage.teamb.services.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RequestMapping("/departement")
//@RestController
//public class DepartementController {
//    private final DepartmentService departementService;
//    private final EmployeeService employeeService;
//
//
//    @Autowired
//    public DepartementController(DepartmentService departementService, EmployeeService employeeService) {
//        this.departementService = departementService;
//        this.employeeService = employeeService;
//    }
//
//
//    @PostMapping("/addOne")
//    public DepartmentDTO addOne(@RequestBody DepartmentDTO departementDTO) {
//        return (departementService.saveOne(departementDTO));
//    }
//
//    @GetMapping("/{id}")
//    public Optional<DepartmentDTO> findOne(@PathVariable Long id) {
//        return (departementService.findOne(id));
//    }
//
//    @GetMapping()
//    public Optional<List<DepartmentDTO>> findAll() {
//        return Optional.ofNullable(departementService.findAll());
//    }
//
//    @PostMapping
//    public ResponseEntity<String> addEmployeeToDepartement(@RequestBody DepartementWithEmployeesDTO departementWithEmployeesDTO) {
//           DepartmentDTO departmentDTO = departementService.addEmployeeToDepartment(departementWithEmployeesDTO.getId(),departementWithEmployeesDTO.getEmployeeAddId());
//            return ResponseEntity.ok().build();
//
//    }
//
//    @PutMapping
//    public DepartmentDTO update(@org.springframework.web.bind.annotation.RequestBody DepartmentDTO departement) {
//        return departementService.saveOne(departement);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteOne(@PathVariable Long id) {
//        departementService.deleteOne(id);
//    }
//}

import com.stage.teamb.dtos.DepartmentDTO;
import com.stage.teamb.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllDepartments() {
        try {
            List<DepartmentDTO> departmentDTOList = departmentService.findAllDepartments();
            return ResponseEntity.ok(departmentDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving departments: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving departments: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        try {
            DepartmentDTO departmentDTO = departmentService.findDepartmentById(id);
            return ResponseEntity.ok(departmentDTO);
        } catch (RuntimeException exception) {
            log.error("Department not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            DepartmentDTO createdDepartment = departmentService.saveDepartment(departmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (RuntimeException exception) {
            log.error("Could not create department: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create department: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        try {
            departmentDTO.setId(id); // Ensure the ID matches the path variable
            DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO);
            return ResponseEntity.ok(updatedDepartment);
        } catch (RuntimeException exception) {
            log.error("Could not update department: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update department: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable Long id) {
        try {
            departmentService.deleteDepartmentById(id);
            return ResponseEntity.ok("Department deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete department: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete department: " + exception.getMessage());
        }
    }



}
