package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rue;
    @Column(unique = true, nullable = false)
    private String codeRue;
    private String ville;
    @ManyToOne
    private Employee employee;

    public Address(Long id, String rue, String codeRue, String ville) {
        this.id = id;
        this.rue = rue;
        this.codeRue = codeRue;
        this.ville = ville;
    }
}
