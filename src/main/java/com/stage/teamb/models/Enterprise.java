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
public class Enterprise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enterprise_sequence")
    private Long id;
    private String enterpriseName;
    private String enterpriseLocal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.MERGE)
    private List<Department> departments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    public void addDepartmentToEnterprise(Department department) {
        this.departments.add(department);
        department.setEnterpriseForDepartment(this);
    }

    public void removeDepartmentFromEnterprise(Department department) {
        this.departments.remove(department);
        department.removeEnterpriseFromDepartment();
    }


    @Override
    public String toString() {
        return "Enterprise{" +
                "id=" + id +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", enterpriseLocal='" + enterpriseLocal + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
