package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Rating implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper method to associate Publication with Rating
    public void setPublicationForRating(Publication publication) {
        this.publication = publication;
        publication.getRating().add(this);
    }

    // Helper method to disassociate Publication from Rating
    public void removePublicationFromRating() {
        if (this.publication != null) {
            this.publication.getRating().remove(this);
            this.publication = null;
        }
    }

    // Helper method to associate Employee with Rating
    public void setEmployeeForRating(Employee employee) {
        this.employee = employee;
        employee.getRatings().add(this);
    }

    // Helper method to disassociate Employee from Rating
    public void removeEmployeeFromRating() {
        if (this.employee != null) {
            this.employee.getRatings().remove(this);
            this.employee = null;
        }
    }


    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", value=" + value +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}


