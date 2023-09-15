package com.stage.teamb.services;


import com.stage.teamb.dtos.AddressDTO;
import com.stage.teamb.mappers.AddressMapper;
import com.stage.teamb.models.Address;
import com.stage.teamb.models.Employee;
import com.stage.teamb.repository.AddressRepository;
import com.stage.teamb.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<AddressDTO> findAllAddresses() {
       return AddressMapper.toListDTO(addressRepository.findAll());
    }

    @Override
    public AddressDTO findAddressById(Long id) {
        return AddressMapper.toDTO(addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address Not Found")));
    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO) {
       try {
           return AddressMapper.toDTO(addressRepository.save(AddressMapper.toEntity(addressDTO)));
       }catch (Exception exception){
           log.error("Address with not found.");
           throw new RuntimeException("Can not save this address  :   "+exception.getMessage());
       }
    }

    @Override
    public void deleteAddressById(Long id) {
        if (addressRepository.existsById(id)) {
            try{
                addressRepository.deleteById(id);
            }catch (Exception exception) {
                log.error("Can not delete this address"+exception.getMessage());
                throw new RuntimeException("Can not delete this address  :   "+exception.getMessage());
            }
        } else {
            log.error("Address Not Exist");
            throw new RuntimeException("Address Not Exist");
        }
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDto) {
        Address existingAddress = addressRepository.findById(addressDto.getId())
                .orElseThrow(() -> {
                  log.error("Address not found ");
                    return new RuntimeException("Address not found with id " + addressDto.getId());
                });
        existingAddress.setRue(addressDto.getRue());
        existingAddress.setVille(addressDto.getVille());
        try {
            return AddressMapper.toDTO(addressRepository.save(existingAddress));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }

    @Override
    public AddressDTO findAddressByEmployeeId(Long employeeId) {

        return AddressMapper.toDTO(addressRepository.findAddressByEmployeeId(employeeId)  .orElseThrow(() -> {
            log.error("Address not found ");
            return new RuntimeException("Address not found with id ");
        }));
    }

    @Override
    public AddressDTO associateEmployeeWithAddress(Long addressId, Long employeeId) {
        if (addressId == null || addressId <= 0 || employeeId == null || employeeId <= 0) {
            String errorMessage = "Invalid addressId or employeeId.";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        try {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("Address not found with id " + addressId));

            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
            // Associate the employee with the address
            address.setEmployee(employee);
            return AddressMapper.toDTO(addressRepository.save(address));
        } catch (Exception ex) {
            String errorMessage = "Error associating employee with address: " + ex.getMessage();
            log.error(errorMessage, ex);
            throw new RuntimeException(errorMessage, ex);
        }
    }

    @Override
    public AddressDTO disassociateEmployeeFromAddress(Long addressId) {
        if (addressId == null || addressId <= 0) {
            String errorMessage = "Invalid addressId.";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        try {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("Address not found with id " + addressId));
            // Disassociate the employee from the address
            address.setEmployee(null);
            return AddressMapper.toDTO(addressRepository.save(address));
        } catch (Exception ex) {
            String errorMessage = "Error disassociating employee from address: " + ex.getMessage();
            log.error(errorMessage, ex);
            throw new RuntimeException(errorMessage, ex);
        }
    }


    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> findOne(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address saveOne(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteOne(Long id) {
        addressRepository.deleteById(id);
    }


    @Override
    public Boolean existsById(Long id) {
        return existsById(id);
    }




}
