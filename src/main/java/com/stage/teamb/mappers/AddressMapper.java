package com.stage.teamb.mappers;

import com.stage.teamb.models.Address;
import com.stage.teamb.dtos.address.AddressDTO;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class AddressMapper {

    public static AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .streetCode(address.getStreetCode())
                .town(address.getTown())
                .employeeId(address.getEmployee() != null ? address.getEmployee().getId() : null)
                .build();
    }

    public static List<AddressDTO> toListDTO(List<Address> addressList) {
        return addressList.stream()
                .map(AddressMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .street(addressDTO.getStreet())
                .streetCode(addressDTO.getStreetCode())
                .town(addressDTO.getTown())
                .build();
    }

    public static List<Address> toListEntity(List<AddressDTO> addressDTOList) {
        return addressDTOList.stream()
                .map(AddressMapper::toEntity)
                .collect(Collectors.toList());
    }

}
