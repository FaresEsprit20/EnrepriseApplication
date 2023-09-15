package com.stage.teamb.services;

import com.stage.teamb.dtos.AddressDTO;
import com.stage.teamb.models.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<AddressDTO> findAllAddresses();

    AddressDTO findAddressById(Long id);

    AddressDTO saveAddress(AddressDTO addressDTO);

    void deleteAddressById(Long id);

    AddressDTO updateAddress(AddressDTO addressDTO);

    AddressDTO findAddressByEmployeeId(Long id);


    AddressDTO associateEmployeeWithAddress(Long addressId, Long employeeId);

    AddressDTO disassociateEmployeeFromAddress(Long addressId);

    List<Address> findAll();

    Optional<Address> findOne(Long id);

    Address saveOne(Address address);

    void deleteOne(Long id);

    Boolean existsById(Long id);



}
