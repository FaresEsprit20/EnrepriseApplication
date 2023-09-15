package com.stage.teamb.mappers;

import com.stage.teamb.models.Employee;
import com.stage.teamb.dtos.EmployeeDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .nom(employee.getNom())
                .prenom(employee.getPrenom())
                .email(employee.getEmail())
                .departementId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    public static List<EmployeeDTO> toListDTO(List<Employee> employeeList) {
        return employeeList.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setNom(employeeDTO.getNom());
        employee.setPrenom(employeeDTO.getPrenom());
        employee.setEmail(employeeDTO.getEmail());
        employee.setCreatedAt(employee.getCreatedAt());
        employee.setUpdatedAt(employee.getUpdatedAt());
        return employee;
    }
    public static List<Employee> toListEntity(List<EmployeeDTO> employeeDTOList) {
        return employeeDTOList.stream()
                .map(EmployeeMapper::toEntity)
                .collect(Collectors.toList());
    }
}
