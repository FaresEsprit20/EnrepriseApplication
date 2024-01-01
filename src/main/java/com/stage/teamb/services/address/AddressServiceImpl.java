//package com.stage.teamb.services.address;
//
//import com.stage.teamb.dtos.address.AddressDTO;
//import com.stage.teamb.exception.CustomException;
//import com.stage.teamb.mappers.AddressMapper;
//import com.stage.teamb.models.Address;
//import com.stage.teamb.models.Employee;
//import com.stage.teamb.repository.jpa.address.AddressRepository;
//import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Slf4j
//public class AddressServiceImpl implements AddressService {
//
//    private final AddressRepository addressRepository;
//    private final EmployeeRepository employeeRepository;
//
//    @Autowired
//    public AddressServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
//        this.addressRepository = addressRepository;
//        this.employeeRepository = employeeRepository;
//    }
//
//    @Override
//    public List<AddressDTO> findAllAddresses() {
//        return AddressMapper.toListDTO(addressRepository.findAll());
//    }
//
//    @Override
//    public AddressDTO findAddressById(Long id) {
//        Address address = addressRepository.findById(id)
//                .orElseThrow(() -> new CustomException("Address not found", 404));
//        return AddressMapper.toDTO(address);
//    }
//
//    @Override
//    public AddressDTO saveAddress(AddressDTO addressDTO) {
//        try {
//            Address address = AddressMapper.toEntity(addressDTO);
//            return AddressMapper.toDTO(addressRepository.save(address));
//        } catch (Exception exception) {
//            log.error("Failed to save address: {}", exception.getMessage());
//            throw new CustomException("Failed to save address", 500);
//        }
//    }
//
//    @Override
//    public void deleteAddressById(Long id) {
//        if (addressRepository.existsById(id)) {
//            try {
//                addressRepository.deleteById(id);
//            } catch (Exception exception) {
//                log.error("Failed to delete address: {}", exception.getMessage());
//                throw new CustomException("Failed to delete address", 500);
//            }
//        } else {
//            log.error("Address Not Found");
//            throw new CustomException("Address Not Found", 404);
//        }
//    }
//
//    @Override
//    public AddressDTO updateAddress(AddressDTO addressDto) {
//        try {
//            Address existingAddress = addressRepository.findById(addressDto.getId())
//                    .orElseThrow(() -> new CustomException("Address not found", 404));
//            existingAddress.setStreet(addressDto.getStreet());
//            existingAddress.setStreetCode(addressDto.getStreetCode());
//            existingAddress.setTown(addressDto.getTown());
//            return AddressMapper.toDTO(addressRepository.save(existingAddress));
//        } catch (Exception exception) {
//            log.error("Failed to update address: {}", exception.getMessage());
//            throw new CustomException("Failed to update address", 500);
//        }
//    }
//
//    @Override
//    public List<AddressDTO> findAddressesByEmployeeId(Long employeeId) {
//        return AddressMapper.toListDTO(addressRepository.findAddressesByEmployeeId(employeeId));
//    }
//
//    @Override
//    public AddressDTO associateEmployeeWithAddress(Long addressId, Long employeeId) {
//        if (addressId == null || addressId <= 0 || employeeId == null || employeeId <= 0) {
//            log.error("Invalid addressId or employeeId");
//            throw new CustomException("Invalid addressId or employeeId", 400);
//        }
//        try {
//            Address address = addressRepository.findById(addressId)
//                    .orElseThrow(() -> new CustomException("Address not found", 404));
//            Employee employee = employeeRepository.findById(employeeId)
//                    .orElseThrow(() -> new CustomException("Employee not found", 404));
//            // Use the helper method to associate the employee with the address
//            address.setEmployeeForAddress(employee);
//            return AddressMapper.toDTO(addressRepository.save(address));
//        } catch (Exception ex) {
//            log.error("Error associating employee with address: {}", ex.getMessage());
//            throw new CustomException("Error associating employee with address", 500);
//        }
//    }
//
//    @Override
//    public AddressDTO disassociateEmployeeFromAddress(Long addressId) {
//        if (addressId == null || addressId <= 0) {
//            log.error("Invalid addressId");
//            throw new CustomException("Invalid addressId", 400);
//        }
//        try {
//            Address address = addressRepository.findById(addressId)
//                    .orElseThrow(() -> new CustomException("Address not found", 404));
//            // Use the helper method to disassociate the employee from the address
//            address.removeEmployeeFromAddress();
//            return AddressMapper.toDTO(addressRepository.save(address));
//        } catch (Exception ex) {
//            log.error("Error disassociating employee from address: {}", ex.getMessage());
//            throw new CustomException("Error disassociating employee from address", 500);
//        }
//    }
//
//    @Override
//    public List<Address> findAll() {
//        return addressRepository.findAll();
//    }
//
//    @Override
//    public Optional<Address> findOne(Long id) {
//        return addressRepository.findById(id);
//    }
//
//    @Override
//    public Address saveOne(Address address) {
//        return addressRepository.save(address);
//    }
//
//    @Override
//    public void deleteOne(Long id) {
//        addressRepository.deleteById(id);
//    }
//
//    @Override
//    public Boolean existsById(Long id) {
//        return addressRepository.existsById(id);
//    }
//}
