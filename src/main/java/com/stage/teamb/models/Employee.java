package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Employee extends Users implements Serializable  {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private Department department;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private List<Rating> ratings;

  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<Publication> publications;

  @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
  private List<Address> addresses;
  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }




}
