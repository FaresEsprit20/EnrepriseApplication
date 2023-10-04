package com.stage.teamb.repository.jpa.department;

import com.stage.teamb.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees e WHERE d.id = :departmentId")
    Department findByIdWithEmployees(Long departmentId);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.enterprise WHERE d.id = :departmentId")
    Department findByIdWithEnterprise(Long departmentId);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees e " +
            "LEFT JOIN FETCH d.enterprise WHERE d.id = :departmentId")
    Department findByIdEagerly(Long departmentId);

}
