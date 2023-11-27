package com.stage.teamb.services.enterprise;

import com.stage.teamb.dtos.department.DepartmentDTO;
import com.stage.teamb.dtos.enterprise.EnterpriseDTO;
import com.stage.teamb.mappers.DepartmentMapper;
import com.stage.teamb.mappers.EnterpriseMapper;
import com.stage.teamb.models.Department;
import com.stage.teamb.models.Enterprise;
import com.stage.teamb.repository.jpa.department.DepartmentRepository;
import com.stage.teamb.repository.jpa.enterprise.EnterpriseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final DepartmentRepository departmentRepository;


    @Autowired
    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository, DepartmentRepository departmentRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.departmentRepository = departmentRepository;
    }


    @Override
    public List<EnterpriseDTO> findAllEnterprises() {
        return EnterpriseMapper.toListDTO(enterpriseRepository.findAll());
    }

    @Override
    public EnterpriseDTO findEnterpriseById(Long id) {
        return EnterpriseMapper.toDTO(enterpriseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public EnterpriseDTO saveEnterprise(EnterpriseDTO enterpriseDTO) {
        try {
            return EnterpriseMapper.toDTO(enterpriseRepository.save(EnterpriseMapper.toEntity(enterpriseDTO)));
        } catch (Exception exception) {
            log.error("Address with not found.");
            throw new RuntimeException("Can not save this entity  :   " + exception.getMessage());
        }
    }

    @Override
    public void deleteEnterpriseById(Long id) {
        if (enterpriseRepository.existsById(id)) {
            try {
                enterpriseRepository.deleteById(id);
            } catch (Exception exception) {
                log.error("Can not delete this entity" + exception.getMessage());
                throw new RuntimeException("Can not delete this entity  :   " + exception.getMessage());
            }
        } else {
            log.error("Entity Not Exist");
            throw new RuntimeException("Entity Not Exist");
        }
    }

    @Override
    public EnterpriseDTO updateEnterprise(EnterpriseDTO enterpriseDTO) {
        Enterprise existingEnterprise = enterpriseRepository.findById(enterpriseDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + enterpriseDTO.getId());
                });
        existingEnterprise.setEnterpriseLocal(enterpriseDTO.getEnterpriseLocal());
        existingEnterprise.setEnterpriseName(enterpriseDTO.getEnterpriseName());
        try {
            return EnterpriseMapper.toDTO(enterpriseRepository.save(existingEnterprise));
        } catch (Exception exception) {
            log.error("Could not update " + exception.getMessage());
            throw new RuntimeException("Could not update " + exception.getMessage());
        }
    }

    @Override
    public List<DepartmentDTO> findDepartmentsByEnterpriseId(Long enterpriseId) {
      return DepartmentMapper.toListDTO(departmentRepository.findAllDepartmentsByEnterprise(enterpriseId));
    }

    @Override
    public DepartmentDTO associateDepartmentWithEnterprise(Long enterpriseId, DepartmentDTO departmentDTO) {
        // Find the enterprise
        Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new RuntimeException("Enterprise not found with id " + enterpriseId));
        // Create a new Department entity and set its values
        Department newDepartment = DepartmentMapper.toEntity(departmentDTO);
        // Associate the department with the enterprise
        newDepartment.setEnterpriseForDepartment(enterprise);
        // Save the new department to the database
        Department savedDepartment = departmentRepository.save(newDepartment);
        // Map the saved department back to a DTO and return it
        return DepartmentMapper.toDTO(savedDepartment);
    }


    @Override
    public DepartmentDTO disassociateDepartmentFromEnterprise(Long departmentId) {
        // Find the department
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        // Disassociate the department from the enterprise
        department.removeEnterpriseFromDepartment();
        // Save the department to update the association
        Department savedDepartment = departmentRepository.save(department);
        // Map the saved department back to a DTO and return it
        return DepartmentMapper.toDTO(savedDepartment);
    }


    @Override
    public EnterpriseDTO findEnterpriseByDepartmentId(Long departmentId) {
        Enterprise enterprise = enterpriseRepository.findEnterpriseByDepartment(departmentId).orElseThrow(
                () -> new RuntimeException("Enterprise or Department Not Found"));
        return EnterpriseMapper.toDTO(enterprise);
    }


    @Override
    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }

    @Override
    public Optional<Enterprise> findOne(Long id) {
        return enterpriseRepository.findById(id);
    }

    @Override
    public Enterprise saveOne(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public void deleteOne(Long id) {
        enterpriseRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return enterpriseRepository.existsById(id);
    }


}