package com.stage.teamb.services;

import com.stage.teamb.dtos.DepartmentDTO;
import com.stage.teamb.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {


    List<DepartmentDTO> findAllDepartments();

    DepartmentDTO findDepartmentById(Long id);

    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    void deleteDepartmentById(Long id);

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO);

    List<Department> findAll();

    Optional<Department> findOne(Long id);

    Department saveOne(Department Department);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}