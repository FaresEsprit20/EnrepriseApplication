package com.stage.teamb.controllers;



import com.stage.teamb.dtos.address.AddressDTO;
import com.stage.teamb.services.address.AddressService;
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

    @GetMapping("/find/all")
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

    @GetMapping("/get/{id}")
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

    @PostMapping("/create")
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

    @PutMapping("/update/{id}")
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

    @DeleteMapping("/delete/{id}")
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
