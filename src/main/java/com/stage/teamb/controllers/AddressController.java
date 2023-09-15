package com.stage.teamb.controllers;
//package com.stage.teamb.controllers;
//
//import com.stage.teamb.dtos.AddressDTO;
//import com.stage.teamb.dtos.EmployeeDTO;
//import com.stage.teamb.mappers.AddressMapper;
//import com.stage.teamb.mappers.EmployeeMapper;
//import com.stage.teamb.models.Address;
//import com.stage.teamb.services.AddressService;
//import com.stage.teamb.services.EmployeeService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/adresse")
//public class AddressController {
//    private final AddressService addressService;
//    private final EmployeeService employeeService;
//
//    public AddressController(AddressService addressService, EmployeeService employeeService) {
//        this.addressService = addressService;
//        this.employeeService = employeeService;
//    }
//
//
//    @GetMapping("/{id}/employees")
//    public ResponseEntity<Optional<EmployeeDTO>> getEmployeesForAdresse(@PathVariable Long id) {
//        Optional<AddressDTO> adresseOptional = addressService.findOne(id);
//        if (adresseOptional.isPresent()) {
//            AddressDTO addressDTO = adresseOptional.get();
//            Optional<EmployeeDTO> employees = employeeService.findOne(addressDTO.getEmployeeId());
//            return ResponseEntity.ok(employees);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//    @GetMapping()
//    public Optional<List<AddressDTO>> findAll() {
//        return Optional.ofNullable(addressService.findAll());
//    }
//
//    @PostMapping("addEmployeeToAdresse")
//    public ResponseEntity<String> addEmployeeToAdresse(@RequestBody AddressDTO adresseDTO) {
//        Optional<EmployeeDTO> employeeOptional = employeeService.findOne(adresseDTO.getEmployeeId());
//        if (employeeOptional.isPresent()) {
//            Address adresse = AddressMapper.toEntity(adresseDTO);
//            adresse.setEmployee(EmployeeMapper.toEntity(employeeOptional.get()));
//            addressService.saveOne(AddressMapper.toDTO(adresse));
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    @PutMapping
//    public AddressDTO update(@RequestBody AddressDTO adresse) {
//        return addressService.update(adresse);
//    }
//    @DeleteMapping("/{id}")
//    public void deleteOne(@PathVariable Long id) {
//        addressService.deleteOne(id);
//    }
//}


import com.stage.teamb.dtos.AddressDTO;
import com.stage.teamb.services.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllAddresses() {
        try {
            List<AddressDTO> addressDTOList = addressService.findAllAddresses();
            return ResponseEntity.ok(addressDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving addresses: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving addresses: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        try {
            AddressDTO addressDTO = addressService.findAddressById(id);
            return ResponseEntity.ok(addressDTO);
        } catch (RuntimeException exception) {
            log.error("Address not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Address not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO createdAddress = addressService.saveAddress(addressDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
        } catch (RuntimeException exception) {
            log.error("Could not create address: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create address: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        try {
            addressDTO.setId(id); // Ensure the ID matches the path variable
            AddressDTO updatedAddress = addressService.updateAddress(addressDTO);
            return ResponseEntity.ok(updatedAddress);
        } catch (RuntimeException exception) {
            log.error("Could not update address: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update address: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
        try {
            addressService.deleteAddressById(id);
            return ResponseEntity.ok("Address deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete address: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete address: " + exception.getMessage());
        }
    }


}
