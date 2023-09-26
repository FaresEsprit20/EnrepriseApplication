package com.stage.teamb.mappers;

import com.stage.teamb.models.Department;
import com.stage.teamb.dtos.DepartmentDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
public class DepartmentMapper {

    public static DepartmentDTO toDTO(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .entrepriseId(department.getEnterprise() != null ? department.getEnterprise().getId() : null)
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }

    public static List<DepartmentDTO> toListDTO(List<Department> departmentList) {
        return departmentList.stream()
                .map(DepartmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Department toEntity(DepartmentDTO departmentDTO) {
        return Department.builder()
                .id(departmentDTO.getId())
                .departmentName(departmentDTO.getDepartmentName())
                .createdAt(departmentDTO.getCreatedAt())
                .updatedAt(departmentDTO.getUpdatedAt())
                .build();
    }

    public static List<Department> toListEntity(List<DepartmentDTO> departmentDTOList) {
        return departmentDTOList.stream()
                .map(DepartmentMapper::toEntity)
                .collect(Collectors.toList());
    }
}
