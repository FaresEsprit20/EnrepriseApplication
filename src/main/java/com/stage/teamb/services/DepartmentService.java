package com.stage.teamb.services;

import com.stage.teamb.dtos.DepartmentDTO;
import com.stage.teamb.dtos.EmployeeDTO;
import com.stage.teamb.dtos.EnterpriseDTO;
import com.stage.teamb.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {


    List<DepartmentDTO> findAllDepartments();

    DepartmentDTO findDepartmentById(Long id);

    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    void deleteDepartmentById(Long id);

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO);

    List<EmployeeDTO> findEmployeesByDepartmentId(Long departmentId);

    EmployeeDTO addEmployeeToDepartment(Long departmentId, EmployeeDTO employeeDTO);

    EmployeeDTO removeEmployeeFromDepartment(Long departmentId, Long employeeId);

    DepartmentDTO associateDepartmentWithEnterprise(Long enterpriseId, Long departmentId);

    DepartmentDTO disassociateDepartmentFromEnterprise(Long departmentId);

    EnterpriseDTO getEnterpriseByDepartmentId(Long departmentId);

    List<DepartmentDTO> findDepartmentsByEnterpriseId(Long enterpriseId);

    List<Department> findAll();

    Optional<Department> findOne(Long id);

    Department saveOne(Department Department);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}