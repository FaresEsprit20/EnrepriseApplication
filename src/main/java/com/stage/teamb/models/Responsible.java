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
@DiscriminatorValue("1")
public class Responsible extends Users   {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.MERGE)
    private List<Event> events;

    @PrePersist
    protected void onCreate() {
        this.setRole(UserRole.RESPONSIBLE);
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    @Builder // Explicitly specify @Builder
    public Responsible(Long id, int registrationNumber, String email, LocalDate birthDate, String lastName, String name,
                       Integer tel, String occupation, String password, UserRole role, List<Token> tokens,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, registrationNumber, email, birthDate, lastName, name, tel, occupation, password, UserRole.RESPONSIBLE, tokens);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }




        public void addEvent(Event event) {
        event.setResponsible(this);
        this.events.add(event);
    }

    public void removeEvent(Event event) {
        event.setResponsible(null);
        this.events.remove(event);
    }

}
