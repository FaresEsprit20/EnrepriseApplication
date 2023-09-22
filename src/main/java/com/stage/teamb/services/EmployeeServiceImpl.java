package com.stage.teamb.services;

import com.stage.teamb.dtos.*;
import com.stage.teamb.mappers.*;
import com.stage.teamb.models.*;
import com.stage.teamb.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
         existingEmployee.setNom(employeeDTO.getNom());
         existingEmployee.setPrenom(employeeDTO.getPrenom());
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
        if (address.getEmployee() != null) {
            throw new IllegalArgumentException("Address is already associated with an employee.");
        }
        // Associate the employee with the address
        address.setEmployee(employee);
        return AddressMapper.toDTO(addressRepository.save(address));
    }

    @Override
    public AddressDTO disassociateEmployeeFromAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id " + addressId));
        // Perform validation here, if necessary
        if (address.getEmployee() == null) {
            throw new IllegalArgumentException("Address is not associated with an employee.");
        }
        // Disassociate the employee from the address
        address.setEmployee(null);
        return AddressMapper.toDTO(addressRepository.save(address));
    }


    @Override
    public List<EmployeeDTO> findEmployeesByDepartmentId(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        return EmployeeMapper.toListDTO(department.getEmployees());
    }


    @Override
    public DepartmentDTO assignDepartmentToEmployee(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));
        // Assign the department to the employee
        employee.setDepartment(department);
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
        employee.setDepartment(null);
        try {
            return DepartmentMapper.toDTO(departmentRepository.save(employee.getDepartment()));
        } catch (Exception exception) {
            log.error("Could not unassign department: " + exception.getMessage());
            throw new RuntimeException("Could not unassign department: " + exception.getMessage());
        }
    }

    @Override
    public RatingDTO createRating(Long employeeId, Long publicationId, RatingDTO ratingDTO) {
        // Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));
        // Find the publication
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));
        // Create a new Rating entity and set its values
        Rating newRating = new Rating();
        newRating.setEmployee(employee);
        newRating.setPublication(publication);
        newRating.setValue(ratingDTO.getValue());
        // Save the new rating to the database
        Rating savedRating = ratingRepository.save(newRating);
        // Map the saved rating back to a DTO and return it
        return RatingMapper.toDTO(savedRating);
    }

    @Override
    public RatingDTO updateRating(Long ratingId, RatingDTO ratingDTO) {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        // Update the existing rating with the values from the DTO
        existingRating.setValue(ratingDTO.getValue());
        // Save the updated rating to the database
        Rating savedRating = ratingRepository.save(existingRating);
        // Map the saved rating back to a DTO and return it
        return RatingMapper.toDTO(savedRating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + ratingId));
        // Delete the rating
        ratingRepository.delete(existingRating);
    }

    @Override
    public PublicationDTO createPublication(Long employeeId, PublicationDTO publicationDTO) {
        // Find the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + employeeId));

        // Create a new Publication entity and set its values
        Publication newPublication = new Publication();
        newPublication.setEmployee(employee);
        newPublication.setNom(publicationDTO.getNom());
        newPublication.setDescription(publicationDTO.getDescription());

        // Save the new publication to the database
        Publication savedPublication = publicationRepository.save(newPublication);

        // Map the saved publication back to a DTO and return it
        return PublicationMapper.toDTO(savedPublication);
    }

    @Override
    public PublicationDTO updatePublication(Long publicationId, PublicationDTO publicationDTO) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));

        // Update the existing publication with the values from the DTO
        existingPublication.setNom(publicationDTO.getNom());
        existingPublication.setDescription(publicationDTO.getDescription());

        // Save the updated publication to the database
        Publication savedPublication = publicationRepository.save(existingPublication);

        // Map the saved publication back to a DTO and return it
        return PublicationMapper.toDTO(savedPublication);
    }

    @Override
    public void deletePublication(Long publicationId) {
        Publication existingPublication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found with id " + publicationId));

        // Delete the publication
        publicationRepository.delete(existingPublication);
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
