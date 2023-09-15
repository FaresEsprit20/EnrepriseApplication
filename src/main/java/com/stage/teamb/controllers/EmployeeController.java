package com.stage.teamb.controllers;//package com.stage.teamb.controllers;
//
//import com.stage.teamb.dtos.EmployeeDTO;
//import com.stage.teamb.models.Published;
//import com.stage.teamb.services.AddressService;
//import com.stage.teamb.services.EmployeeService;
//import com.stage.teamb.services.PublicationService;
//import com.stage.teamb.services.RatingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/employee")
//public class EmployeeController {
//
//    private final EmployeeService employeeService;
//    private final PublicationService publicationService;
//    private final RatingService ratingService;
//    private final AddressService adresseService;
//
//    @Autowired
//    public EmployeeController(EmployeeService employeeService, PublicationService publicationService, RatingService ratingService, AddressService adresseService) {
//        this.employeeService = employeeService;
//        this.publicationService = publicationService;
//        this.ratingService = ratingService;
//        this.adresseService = adresseService;
//    }
//
//
//    public ResponseEntity<EmployeeDTO> findOne(@PathVariable Long id) {
//        Optional<EmployeeDTO> employee = employeeService.findOne(id);
//        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping()
//    public List<EmployeeDTO> findAll() {
//        return employeeService.findAll();
//    }
//    @PostMapping
//    public EmployeeDTO saveOne(@RequestBody EmployeeDTO employeeDTO) {
//        return employeeService.saveOne(employeeDTO);
//    }
//
//
//    @PostMapping("/addPublished")
//    public ResponseEntity<String> addPublishedToEmployee(@io.swagger.v3.oas.annotations.parameters.RequestBody EmployeeWithAdresseDTO employeeWithAdresseDTO) {
//        List<Published> publisheds = publicationService.findPublishedList(employeeWithAdresseDTO.getPublicationIds());
//
//        if (!publisheds.isEmpty()) {
////            Employee employee = EmployeeMapper.toEntity(employeeWithAdresseDTO.);
////            employee.setPublications(publisheds);
////            employeeService.saveOne(EmployeeMapper.toDTO(employee));
//
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
////    @PostMapping("/addRating")
////    public ResponseEntity<String> addRatingToEmployee(@RequestBody EmployeeWithAdresseDTO employeeWithAdresseDTO) {
////        List<Rating> ratings = ratingService.findRatings(employeeWithAdresseDTO.getRatingIds());
////
////        if (!ratings.isEmpty()) {
////            Employee employee = EmployeeMapper.toEntity(employeeWithAdresseDTO);
////            employee.setRatings(ratings);
////            employeeService.save(employee);
////            return ResponseEntity.ok().build();
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
////
////    @PostMapping("/addAdresse")
////    public ResponseEntity<String> addAdresseToEmployee(@RequestBody EmployeeWithAdresseDTO employeeWithAdresseDTO) {
////        List<Adresse> adresses = adresseService.findAdresse(employeeWithAdresseDTO.getAdresseIds());
////
////        if (!adresses.isEmpty()) {
////            Employee employee = employeeMapper.toEmployeeEntity(employeeWithAdresseDTO);
////            employee.setAdresses(adresses);
////            employeeService.save(employee);
////            return ResponseEntity.ok().build();
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
////
//
//
//    @PutMapping("/update")
//    public ResponseEntity<EmployeeDTO> update(@io.swagger.v3.oas.annotations.parameters.RequestBody EmployeeDTO employeeDTO) {
//        EmployeeDTO updatedEmployee = employeeService.saveOne(employeeDTO);
//        if (updatedEmployee != null) {
//            return ResponseEntity.ok(updatedEmployee);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOne(@PathVariable Long id) {
//        employeeService.deleteOne(id);
//        return ResponseEntity.noContent().build();
//    }
//}


import com.stage.teamb.dtos.EmployeeDTO;
import com.stage.teamb.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<EmployeeDTO> employeeDTOList = employeeService.findAllEmployees();
            return ResponseEntity.ok(employeeDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving employees: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving employees: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            EmployeeDTO employeeDTO = employeeService.findEmployeeById(id);
            return ResponseEntity.ok(employeeDTO);
        } catch (RuntimeException exception) {
            log.error("Employee not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO createdEmployee = employeeService.saveEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (RuntimeException exception) {
            log.error("Could not create employee: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create employee: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            employeeDTO.setId(id); // Ensure the ID matches the path variable
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException exception) {
            log.error("Could not update employee: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update employee: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {
        try {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete employee: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete employee: " + exception.getMessage());
        }
    }


}
