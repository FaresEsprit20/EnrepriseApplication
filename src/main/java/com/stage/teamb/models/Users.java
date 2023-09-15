package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Data
public abstract class Users implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="users_sequence")
    public Long id;
    @Column(unique = true)
    private String email;
    private LocalDate dateN;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private Integer tel;
    private String poste;


}
