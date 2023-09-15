package com.stage.teamb.repository;

import com.stage.teamb.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    //List<Department> findAllByEntrepriseId(Long entrepriseId);
}
