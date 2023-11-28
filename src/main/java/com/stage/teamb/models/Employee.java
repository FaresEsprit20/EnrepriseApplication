package com.stage.teamb.models;

import com.stage.teamb.config.security.token.Token;
import com.stage.teamb.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


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


  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id", updatable = true, nullable = true)
  private Department department;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
  private List<Rating> ratings;

  @OneToMany(mappedBy = "employee")
  private List<Publication> publications;

  @OneToMany(mappedBy = "employee")
  private List<Address> addresses;

  @PrePersist
  protected void onCreate() {
    this.setRole(UserRole.EMPLOYEE);
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  @Builder // Explicitly specify @Builder
  public Employee(Long id, int registrationNumber, String email, LocalDate birthDate, String lastName, String name,
                  Integer tel, String occupation, String password, UserRole role, List<Token> tokens,
                  LocalDateTime createdAt, LocalDateTime updatedAt, Department department,
                  List<Rating> ratings, List<Publication> publications, List<Address> addresses) {
    super(id, registrationNumber, email, birthDate, lastName, name, tel, occupation, password, UserRole.EMPLOYEE, tokens);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.department = department;
    this.ratings = ratings;
    this.publications = publications;
    this.addresses = addresses;
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
