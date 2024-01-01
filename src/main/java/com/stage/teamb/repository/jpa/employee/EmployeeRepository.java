package com.stage.teamb.repository.jpa.employee;

import com.stage.teamb.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


//    @Query("SELECT e FROM Employee e JOIN FETCH e.department d WHERE d.id = :departmentId")
//    List<Employee> findAllEmployeesByDepartmentId(Long departmentId);

    @Query("SELECT e FROM Employee e JOIN FETCH e.publications p WHERE p.id = :publicationId")
    Employee findEmployeeByPublicationId(Long publicationId);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.ratings WHERE e.id = :employeeId")
    Employee findByIdWithRatings(Long employeeId);

//    @Query("SELECT e FROM Employee e JOIN FETCH e.department d WHERE e.id = :employeeId")
//    Optional<Employee> findByIdWithDAndDepartment(Long employeeId);


    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.publications WHERE e.id = :employeeId")
    Employee findByIdWithPublications(Long employeeId);

//    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.addresses WHERE e.id = :employeeId")
//    Employee findByIdWithAddresses(Long employeeId);

    @Query("SELECT e FROM Employee e " +
//            "LEFT JOIN FETCH e.department " + // Eagerly fetch department
            "LEFT JOIN FETCH e.ratings " +    // Eagerly fetch ratings
            "LEFT JOIN FETCH e.publications " + // Eagerly fetch publications
//            "LEFT JOIN FETCH e.addresses " +   // Eagerly fetch addresses
            "WHERE e.id = :id")
    Employee findByIdEagerly(Long id);

    Optional<Employee> findByEmail(String email);

}
