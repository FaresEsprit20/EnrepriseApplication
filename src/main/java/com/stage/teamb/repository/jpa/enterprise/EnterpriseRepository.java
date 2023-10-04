package com.stage.teamb.repository.jpa.enterprise;

import com.stage.teamb.models.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {


    @Query("SELECT e FROM Enterprise e LEFT JOIN FETCH e.departments d WHERE e.id = :enterpriseId")
    Enterprise findByIdWithDepartments(Long enterpriseId);

    @Query("SELECT e FROM Enterprise e LEFT JOIN FETCH e.departments d" +
            " LEFT JOIN FETCH d.employees WHERE e.id = :enterpriseId")
    Enterprise findByIdEagerly(Long enterpriseId);

}
