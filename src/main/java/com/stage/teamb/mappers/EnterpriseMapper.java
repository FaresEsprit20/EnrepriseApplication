package com.stage.teamb.mappers;

import com.stage.teamb.models.Enterprise;
import com.stage.teamb.dtos.EnterpriseDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
public class EnterpriseMapper {

    public static EnterpriseDTO toDTO(Enterprise enterprise) {
        return EnterpriseDTO.builder()
                .id(enterprise.getId())
                .enterpriseName(enterprise.getEnterpriseName())
                .enterpriseLocal(enterprise.getEnterpriseLocal())
                .createdAt(enterprise.getCreatedAt())
                .updatedAt(enterprise.getUpdatedAt())
                .build();
    }

    public static List<EnterpriseDTO> toListDTO(List<Enterprise> enterpriseList) {
        return enterpriseList.stream()
                .map(EnterpriseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Enterprise toEntity(EnterpriseDTO enterpriseDTO) {
        return Enterprise.builder()
                .id(enterpriseDTO.getId())
                .enterpriseName(enterpriseDTO.getEnterpriseName())
                .enterpriseLocal(enterpriseDTO.getEnterpriseLocal())
                .createdAt(enterpriseDTO.getCreatedAt())
                .updatedAt(enterpriseDTO.getUpdatedAt())
                .build();
    }

    public static List<Enterprise> toListEntity(List<EnterpriseDTO> enterpriseDTOList) {
        return enterpriseDTOList.stream()
                .map(EnterpriseMapper::toEntity)
                .collect(Collectors.toList());
    }
}
