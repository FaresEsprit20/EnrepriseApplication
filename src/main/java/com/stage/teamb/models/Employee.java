package com.stage.teamb.models;

import com.stage.teamb.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@DiscriminatorValue("2")
public class Employee extends Users {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;


//  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//  @JoinColumn(name = "department_id", unique = true)
//  private Department department;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
  private List<Rating> ratings;

  @OneToMany(mappedBy = "employee")
  private List<Publication> publications;

//  @OneToMany(mappedBy = "employee")
//  private List<Address> addresses;

  @PrePersist
  protected void onCreate() {
    this.setRole(UserRole.EMPLOYEE);
    createdAt = LocalDateTime.now();
    // Generate and set the registrationNumber with "emp-" prefix and UUID
    if (getRegistrationNumber() == null) {
      setRegistrationNumber("emp-" + UUID.randomUUID());
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  @Builder // Explicitly specify @Builder
  public Employee(Long id, String registrationNumber, String email, LocalDate birthDate, String lastName, String name,
                  Integer tel, String occupation, String password, UserRole role,
                  LocalDateTime createdAt, LocalDateTime updatedAt,
                            List<Rating> ratings, List<Publication> publications) {
    super(id, registrationNumber, email, birthDate, lastName, name, tel, occupation, password, UserRole.EMPLOYEE);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
//    this.department = department;
    this.ratings = ratings;
    this.publications = publications;
//    this.addresses = addresses;
  }


//  public void setDepartmentForEmployee(Department department) {
//    this.department = department;
//  }
//
//  public void removeDepartmentFromEmployee() {
//    this.department = null;
//  }




  @Override
  public String toString() {
    return "Employee{" +
            "id=" + getId() +
            ", registrationNumber=" + getRegistrationNumber() +
            ", email='" + getEmail() + '\'' +
            ", birthDate=" + getBirthDate() +
            ", lastName='" + getLastName() + '\'' +
            ", name='" + getName() + '\'' +
            ", tel=" + getTel() +
            ", occupation='" + getOccupation() + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
//            ", department=" + (department != null ? department.getId() : null) + // Print only department ID
            ", ratings=" + ratings +
            ", publications=" + publications +
//            ", addresses=" + addresses +
            '}';
  }



}
