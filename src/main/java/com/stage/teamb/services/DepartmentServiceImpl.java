package com.stage.teamb.services;


import com.stage.teamb.dtos.DepartmentDTO;
import com.stage.teamb.dtos.EmployeeDTO;
import com.stage.teamb.dtos.EnterpriseDTO;
import com.stage.teamb.mappers.DepartmentMapper;
import com.stage.teamb.mappers.EmployeeMapper;
import com.stage.teamb.mappers.EnterpriseMapper;
import com.stage.teamb.models.Department;
import com.stage.teamb.models.Employee;
import com.stage.teamb.models.Enterprise;
import com.stage.teamb.repository.DepartmentRepository;
import com.stage.teamb.repository.EmployeeRepository;
import com.stage.teamb.repository.EnterpriseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {


  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;
  private final EnterpriseRepository enterpriseRepository;



    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, EnterpriseRepository enterpriseRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.enterpriseRepository = enterpriseRepository;
    }


    @Override
    public List<DepartmentDTO> findAllDepartments() {
        return DepartmentMapper.toListDTO(departmentRepository.findAll());
    }

    @Override
    public DepartmentDTO findDepartmentById(Long id) {
        return DepartmentMapper.toDTO(departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        try {
            return DepartmentMapper.toDTO(departmentRepository.save(DepartmentMapper.toEntity(departmentDTO)));
        }catch (Exception exception){
            log.error("Address with not found.");
            throw new RuntimeException("Can not save this entity  :   "+exception.getMessage());
        }
    }

    @Override
    public void deleteDepartmentById(Long id) {
        if (departmentRepository.existsById(id)) {
            try{
                departmentRepository.deleteById(id);
            }catch (Exception exception) {
                log.error("Can not delete this entity"+exception.getMessage());
                throw new RuntimeException("Can not delete this entity  :   "+exception.getMessage());
            }
        } else {
            log.error("Entity Not Exist");
            throw new RuntimeException("Entity Not Exist");
        }
    }


    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) {
        Department existingDepartment= departmentRepository.findById(departmentDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + departmentDTO.getId());
                });
        existingDepartment.setDepartmentName(departmentDTO.getDepartmentName());
        try {
            return DepartmentMapper.toDTO(departmentRepository.save(existingDepartment));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }


    @Override
    public List<EmployeeDTO> findEmployeesByDepartmentId(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        return EmployeeMapper.toListDTO(department.getEmployees());
    }

    @Override
    public EmployeeDTO addEmployeeToDepartment(Long departmentId, EmployeeDTO employeeDTO) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        employee.setDepartmentForEmployee(department);
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            return EmployeeMapper.toDTO(savedEmployee);
        } catch (Exception exception) {
            log.error("Could not add employee to department: " + exception.getMessage());
            throw new RuntimeException("Could not add employee to department: " + exception.getMessage());
        }
    }


    @Override
    public EmployeeDTO removeEmployeeFromDepartment(Long departmentId, Long employeeId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        if (!department.getEmployees().contains(employee)) {
            log.error("Employee is not associated with the department.");
            throw new RuntimeException("Employee is not associated with the department.");
        }
        employee.removeDepartmentFromEmployee();
        employeeRepository.save(employee);
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public DepartmentDTO associateDepartmentWithEnterprise(Long enterpriseId, Long departmentId) {
        // Find the enterprise
        Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new RuntimeException("Enterprise not found with id " + enterpriseId));
        // Find the department
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        // Associate the department with the enterprise
        department.setEnterpriseForDepartment(enterprise);
        // Save the department to update the association
        Department savedDepartment = departmentRepository.save(department);
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
    public EnterpriseDTO getEnterpriseByDepartmentId(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        Enterprise enterprise = department.getEnterprise();
        if (enterprise == null) {
            throw new RuntimeException("Enterprise not found for department with id " + departmentId);
        }
        return EnterpriseMapper.toDTO(enterprise);
    }

    @Override
    public List<DepartmentDTO> findDepartmentsByEnterpriseId(Long enterpriseId) {
        Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
                .orElseThrow(() -> new RuntimeException("Enterprise not found with id " + enterpriseId));
        List<Department> departments = enterprise.getDepartments();
        return DepartmentMapper.toListDTO(departments);
    }


    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> findOne(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department saveOne(Department Department) {
        return departmentRepository.save(Department);
    }

    @Override
    public void deleteOne(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }



}