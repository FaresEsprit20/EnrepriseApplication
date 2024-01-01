//package com.stage.teamb.repository.jpa.department;
//
//import com.stage.teamb.models.Department;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface DepartmentRepository extends JpaRepository<Department, Long> {
//
//    @Query("SELECT d FROM Department d JOIN FETCH d.enterprise e WHERE e.id = :enterpriseId")
//    List<Department> findAllDepartmentsByEnterprise(Long enterpriseId);
//
//    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees e WHERE d.id = :departmentId")
//    Optional<Department> findByIdWithEmployees(Long departmentId);
//
//    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.enterprise WHERE d.id = :departmentId")
//    Optional<Department> findByIdWithEnterprise(Long departmentId);
//
//    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees e " +
//            "LEFT JOIN FETCH d.enterprise WHERE d.id = :departmentId")
//    Department findByIdEagerly(Long departmentId);
//
//
//
//}
