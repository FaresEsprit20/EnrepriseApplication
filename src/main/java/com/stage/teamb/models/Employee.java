package com.stage.teamb.models;

import com.stage.teamb.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@DiscriminatorValue("Employee")
public class Employee extends Users {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;


  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id")
  private Department department;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
  private List<Rating> ratings;

  @OneToMany(mappedBy = "employee")
  private List<Publication> publications;

  @OneToMany(mappedBy = "employee")
  private List<Address> addresses;

  @PrePersist
  protected void onCreate() {
    this.setRole(UserRole.ROLE_EMPLOYEE);
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }



  public void setDepartmentForEmployee(Department department) {
    this.department = department;
  }

  public void removeDepartmentFromEmployee() {
    this.department = null;
  }

  public void addRating(Rating rating) {
    rating.setEmployee(this);
    this.ratings.add(rating);
  }

  public void removeRating(Rating rating) {
    rating.setEmployee(null);
    this.ratings.remove(rating);
  }

  public void addPublication(Publication publication) {
    publication.setEmployee(this);
    this.publications.add(publication);
  }

  public void removePublication(Publication publication) {
    publication.setEmployee(null);
    this.publications.remove(publication);
  }

  public void addAddress(Address address) {
    address.setEmployee(this);
    this.addresses.add(address);
  }

  public void removeAddress(Address address) {
    address.setEmployee(null);
    this.addresses.remove(address);
  }



}
