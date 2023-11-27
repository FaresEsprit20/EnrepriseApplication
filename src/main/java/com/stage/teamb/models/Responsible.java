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
@DiscriminatorValue("Responsible")
public class Responsible extends Users   {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.MERGE)
    private List<Event> events;

    @PrePersist
    protected void onCreate() {
        this.setRole(UserRole.ROLE_RESPONSIBLE);
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
