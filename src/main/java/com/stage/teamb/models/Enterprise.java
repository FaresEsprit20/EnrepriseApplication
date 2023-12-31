package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

//    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.MERGE)
//    private List<Department> departments;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.MERGE)
    private List<Employee> employees;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.MERGE)
    private List<Responsible> responsibleList;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

   //helpers
   // Helper methods for Employee
   public void addEmployee(Employee employee) {
       if (employee != null) {
           if (employees == null) {
               employees = new ArrayList<>();
           }

           if (!employees.contains(employee)) {
               employee.setEnterprise(this);
               employees.add(employee);
           }
       }
   }

    public void removeEmployee(Employee employee) {
        if (employee != null && employees != null) {
            employee.setEnterprise(null);
            employees.remove(employee);
        }
    }

    // Helper methods for Responsible
    public void addResponsible(Responsible responsible) {
        if (responsible != null) {
            if (responsibleList == null) {
                responsibleList = new ArrayList<>();
            }

            if (!responsibleList.contains(responsible)) {
                responsible.setEnterprise(this);
                responsibleList.add(responsible);
            }
        }
    }

    public void removeResponsible(Responsible responsible) {
        if (responsible != null && responsibleList != null) {
            responsible.setEnterprise(null);
            responsibleList.remove(responsible);
        }
    }


}
