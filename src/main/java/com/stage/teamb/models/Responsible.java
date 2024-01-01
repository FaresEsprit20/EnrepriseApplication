package com.stage.teamb.models;

import com.stage.teamb.models.enums.UserRole;
import jakarta.persistence.*;
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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", unique = true)
    private Enterprise enterprise;

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
                       Integer tel, String occupation, String password, UserRole role, Enterprise enterprise,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, registrationNumber, email, birthDate, lastName, name, tel, occupation, password, UserRole.RESPONSIBLE);
        this.enterprise = enterprise;
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
                ", enterprise='" + enterprise + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    
}
