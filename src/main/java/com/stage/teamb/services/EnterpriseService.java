package com.stage.teamb.services;

import com.stage.teamb.dtos.DepartmentDTO;
import com.stage.teamb.dtos.EnterpriseDTO;
import com.stage.teamb.models.Enterprise;

import java.util.List;
import java.util.Optional;

public interface EnterpriseService {


    List<EnterpriseDTO> findAllEnterprises();

    EnterpriseDTO findEnterpriseById(Long id);

    EnterpriseDTO saveEnterprise(EnterpriseDTO enterpriseDTO);

    void deleteEnterpriseById(Long id);
    EnterpriseDTO updateEnterprise(EnterpriseDTO enterpriseDTO);



    List<Enterprise> findAll();

    Optional<Enterprise> findOne(Long id);

    Enterprise saveOne(Enterprise enterprise);

    void deleteOne(Long id);

    Boolean existsById(Long id);

    List<DepartmentDTO> findDepartmentsByEnterpriseId(Long enterpriseId);

    DepartmentDTO associateDepartmentWithEnterprise(Long enterpriseId, DepartmentDTO departmentDTO);

    DepartmentDTO disassociateDepartmentFromEnterprise(Long departmentId);

    EnterpriseDTO findEnterpriseByDepartmentId(Long departmentId);
}

