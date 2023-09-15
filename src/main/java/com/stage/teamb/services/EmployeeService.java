package com.stage.teamb.services;

import com.stage.teamb.dtos.AddressDTO;
import com.stage.teamb.dtos.DepartmentDTO;
import com.stage.teamb.dtos.EmployeeDTO;
import com.stage.teamb.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO findEmployeeById(Long id);

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    void deleteEmployeeById(Long id);
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);


    AddressDTO associateEmployeeWithAddress(Long addressId, Long employeeId);

    AddressDTO disassociateEmployeeFromAddress(Long addressId);

    DepartmentDTO assignDepartmentToEmployee(Long employeeId, Long departmentId);

    DepartmentDTO unassignDepartmentFromEmployee(Long employeeId);

    List<Employee> findAll();

    Optional<Employee> findOne(Long id);

    Employee saveOne(Employee employee);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}