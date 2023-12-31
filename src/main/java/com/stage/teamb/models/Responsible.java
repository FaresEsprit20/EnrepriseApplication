package com.stage.teamb.models;

import com.stage.teamb.models.enums.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@DiscriminatorValue("1")
public class Responsible extends Users  {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.setRole(UserRole.RESPONSIBLE);
        createdAt = LocalDateTime.now();
        // Generate and set the registrationNumber with "emp-" prefix and UUID
        if (getRegistrationNumber() == null) {
            setRegistrationNumber("res-" + UUID.randomUUID());
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    @Builder // Explicitly specify @Builder
    public Responsible(Long id, String registrationNumber, String email, LocalDate birthDate, String lastName, String name,
                       Integer tel, String occupation, String password, UserRole role,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, registrationNumber, email, birthDate, lastName, name, tel, occupation, password, UserRole.RESPONSIBLE);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Responsible{" +
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
                '}';
    }

    
}
