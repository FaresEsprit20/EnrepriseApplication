package com.stage.teamb.services;


import com.stage.teamb.dtos.AddressDTO;
import com.stage.teamb.mappers.AddressMapper;
import com.stage.teamb.models.Address;
import com.stage.teamb.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
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
