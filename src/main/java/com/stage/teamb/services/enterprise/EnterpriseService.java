package com.stage.teamb.services.enterprise;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseCreateDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseManagementDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseUpdateDTO;
import com.stage.teamb.dtos.responsible.ResponsibleDTO;
import com.stage.teamb.models.Enterprise;

import java.util.List;
import java.util.Optional;

public interface EnterpriseService {


    List<EnterpriseDTO> findAllEnterprises();

    EnterpriseDTO findEnterpriseById(Long id);

    EnterpriseDTO saveEnterprise(EnterpriseCreateDTO enterpriseDTO);

    void deleteEnterpriseById(Long id);
    EnterpriseDTO updateEnterprise(EnterpriseUpdateDTO enterpriseDTO);


    EnterpriseDTO addEmployeeToEnterprise(EnterpriseManagementDTO enterpriseDTO);

    EnterpriseDTO deleteEmployeeFromEnterprise(EnterpriseManagementDTO enterpriseDTO);

    EnterpriseDTO addResponsibleToEnterprise(EnterpriseManagementDTO enterpriseDTO);

    EnterpriseDTO deleteResponsibleFromEnterprise(EnterpriseManagementDTO enterpriseDTO);

    List<EmployeeDTO> getAllEmployees(Long enterpriseId);

    List<ResponsibleDTO> getAllResponsibles(Long enterpriseId);

    List<Enterprise> findAll();

    Optional<Enterprise> findOne(Long id);

    Enterprise saveOne(Enterprise enterprise);

    void deleteOne(Long id);

    Boolean existsById(Long id);

//    List<DepartmentDTO> findDepartmentsByEnterpriseId(Long enterpriseId);
//
//    DepartmentDTO associateDepartmentWithEnterprise(Long enterpriseId, DepartmentDTO departmentDTO);
//
//    DepartmentDTO disassociateDepartmentFromEnterprise(Long departmentId);
//
//    EnterpriseDTO findEnterpriseByDepartmentId(Long departmentId);
}

