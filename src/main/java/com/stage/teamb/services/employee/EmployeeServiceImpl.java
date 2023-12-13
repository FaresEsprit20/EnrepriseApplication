package com.stage.teamb.services.employee;

import com.stage.teamb.dtos.address.AddressDTO;
import com.stage.teamb.dtos.department.DepartmentDTO;
import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.mappers.AddressMapper;
import com.stage.teamb.mappers.DepartmentMapper;
import com.stage.teamb.mappers.EmployeeMapper;
import com.stage.teamb.models.Address;
import com.stage.teamb.models.Department;
import com.stage.teamb.models.Employee;
import com.stage.teamb.repository.jpa.address.AddressRepository;
import com.stage.teamb.repository.jpa.department.DepartmentRepository;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
import com.stage.teamb.repository.jpa.publication.PublicationRepository;
import com.stage.teamb.repository.jpa.rating.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;
    private final DepartmentRepository departmentRepository;
    private final RatingRepository ratingRepository;
    private final PublicationRepository publicationRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AddressRepository addressRepository, DepartmentRepository departmentRepository, RatingRepository ratingRepository, PublicationRepository publicationRepository) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
        this.ratingRepository = ratingRepository;
        this.publicationRepository = publicationRepository;
    }


    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return EmployeeMapper.toListDTO(employeeRepository.findAll());
    }

    @Override
    public EmployeeDTO findEmployeeById(Long id) {
        return EmployeeMapper.toDTO(employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        try {
            return EmployeeMapper.toDTO(employeeRepository.save(EmployeeMapper.toEntity(employeeDTO)));
        }catch (Exception exception){
            log.error("Address with not found.");
            throw new RuntimeException("Can not save this entity  :   "+exception.getMessage());
        }
    }

    @Override
    public void deleteEmployeeById(Long id) {
        if (employeeRepository.existsById(id)) {
            try{
                employeeRepository.deleteById(id);
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
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + employeeDTO.getId());
                });
         existingEmployee.setName(employeeDTO.getName());
         existingEmployee.setLastName(employeeDTO.getLastName());
         existingEmployee.setEmail(employeeDTO.getEmail());
        try {
            return EmployeeMapper.toDTO(employeeRepository.save(existingEmployee));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }


    @Override
    public AddressDTO associateEmployeeWithAddress(Long addressId, Long employeeId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id " + addressId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        // Perform validation here, if necessary
        // Associate the employee with the address
        address.setEmployeeForAddress(employee);
        return AddressMapper.toDTO(addressRepository.save(address));
    }

    @Override
    public AddressDTO disassociateEmployeeFromAddress(Long addressId) {
        Address address = addressRepository.findByIdWithEmployee(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id " + addressId));
        // Perform validation here, if necessary
        if (address.getEmployee() == null) {
            throw new RuntimeException("Address is not associated with an employee.");
        }
        // Disassociate the employee from the address
        address.removeEmployeeFromAddress();
        return AddressMapper.toDTO(addressRepository.save(address));
    }


    @Override
    public List<EmployeeDTO> findEmployeesByDepartmentId(Long departmentId) {
       return EmployeeMapper.toListDTO(employeeRepository.findAllEmployeesByDepartmentId(departmentId));
    }


    @Override
    public DepartmentDTO assignDepartmentToEmployee(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        // Assign the department to the employee
        employee.setDepartmentForEmployee(department);
        try {
            return DepartmentMapper.toDTO(departmentRepository.save(department));
        } catch (Exception exception) {
            log.error("Could not assign department: " + exception.getMessage());
            throw new RuntimeException("Could not assign department: " + exception.getMessage());
        }
    }

    @Override
    public DepartmentDTO unassignDepartmentFromEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        // Unassign the department from the employee
        employee.removeDepartmentFromEmployee();
        try {
            return DepartmentMapper.toDTO(departmentRepository.save(employee.getDepartment()));
        } catch (Exception exception) {
            log.error("Could not unassign department: " + exception.getMessage());
            throw new RuntimeException("Could not unassign department: " + exception.getMessage());
        }
    }

    @Override
    public EmployeeDTO findEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Employee not found with Email " + email)));
            return EmployeeMapper.toDTO(employee);
    }





    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findOne(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee saveOne(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteOne(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }
   


}
