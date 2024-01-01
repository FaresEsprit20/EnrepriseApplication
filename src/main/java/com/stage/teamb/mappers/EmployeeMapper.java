package com.stage.teamb.mappers;

import com.stage.teamb.models.Employee;
import com.stage.teamb.dtos.employee.EmployeeDTO;
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
                .registrationNumber(employee.getRegistrationNumber())
                .name(employee.getName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .occupation(employee.getOccupation())
                .enterpriseDTO(employee.getEnterprise()!= null ? EnterpriseMapper.toDTO(employee.getEnterprise()) : null)
               // .departmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
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
        employee.setRegistrationNumber(employeeDTO.getRegistrationNumber());
        employee.setName(employeeDTO.getName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setOccupation(employeeDTO.getOccupation());
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
