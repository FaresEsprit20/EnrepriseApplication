package com.stage.teamb.services.address;

import com.stage.teamb.dtos.address.AddressDTO;
import com.stage.teamb.models.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<AddressDTO> findAllAddresses();

    AddressDTO findAddressById(Long id);

    AddressDTO saveAddress(AddressDTO addressDTO);

    void deleteAddressById(Long id);

    AddressDTO updateAddress(AddressDTO addressDTO);


    List<AddressDTO> findAddressesByEmployeeId(Long employeeId);

    AddressDTO associateEmployeeWithAddress(Long addressId, Long employeeId);

    AddressDTO disassociateEmployeeFromAddress(Long addressId);

    List<Address> findAll();

    Optional<Address> findOne(Long id);

    Address saveOne(Address address);

    void deleteOne(Long id);

    Boolean existsById(Long id);



}
