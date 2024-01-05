package com.stage.teamb.services.employee;

import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.exception.CustomException;
import com.stage.teamb.mappers.EmployeeMapper;
import com.stage.teamb.models.Employee;
import com.stage.teamb.repository.jpa.employee.EmployeeRepository;
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

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return EmployeeMapper.toListDTO(employeeRepository.findAll());
    }

    @Override
    public EmployeeDTO findEmployeeById(Long id) {
        return EmployeeMapper.toDTO(employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(404, Collections.singletonList("Employee Not Found "))));
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        try {
            return EmployeeMapper.toDTO(employeeRepository.save(EmployeeMapper.toEntity(employeeDTO)));
        }catch (Exception exception){
            log.error("Address with not found.");
            throw new CustomException(500, Collections.singletonList("Can not save this entity Employee  :   "
                    +exception.getMessage()));
        }
    }

    @Override
    public void deleteEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)) {
            log.error("Entity Not Exist");
            throw new CustomException(404,Collections.singletonList(("Entity Employee Not Exist")));
        }
            try{
                employeeRepository.deleteById(id);
            }catch (Exception exception) {
                log.error("Can not delete this entity"+exception.getMessage());
                throw new CustomException(500,Collections.singletonList("Can not delete this entity  :   "
                        +exception.getMessage()));
            }
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new CustomException(404,Collections.singletonList("entity not found with id "
                            + employeeDTO.getId()));
                });
         existingEmployee.setName(employeeDTO.getName());
         existingEmployee.setLastName(employeeDTO.getLastName());
         existingEmployee.setEmail(employeeDTO.getEmail());
        try {
            return EmployeeMapper.toDTO(employeeRepository.save(existingEmployee));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new CustomException(500,Collections.singletonList("Could not update "+exception.getMessage()));
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
