package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Employee extends Users implements Serializable  {

  @ManyToOne(cascade = CascadeType.MERGE)
  private Department department;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
  private List<Address> addresses;

  @OneToMany(mappedBy = "employee")
  private List<Rating> ratings;

  @OneToMany(mappedBy = "employee")
  private List<Published> publications;

  @Column
  private LocalDateTime createdAt;
  @Column
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }




}
