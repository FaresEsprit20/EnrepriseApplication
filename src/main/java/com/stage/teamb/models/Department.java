package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departement_sequence")
    private Long id;
    private String departmentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void setEnterpriseForDepartment(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public void removeEnterpriseFromDepartment() {
        this.enterprise = null;
    }

    public void addEmployeeToDepartment(Employee employee) {
        this.employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployeeFromDepartment(Employee employee) {
        this.employees.remove(employee);
        employee.removeDepartmentFromEmployee();
    }


    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", enterprise=" + (enterprise != null ? enterprise.getId() : null) +
                ", employees=" + employees +
                '}';
    }


}
