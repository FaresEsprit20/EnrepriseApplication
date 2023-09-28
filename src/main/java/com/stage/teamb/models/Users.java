package com.stage.teamb.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Users implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="users_sequence")
    public Long id;
    @Column(unique = true)
    private int registrationNumber;
    @Column(unique = true)
    private String email;
    private LocalDate birthDate;
    private String lastName;
    private String name;
    @Column(unique = true)
    private Integer tel;
    private String occupation;




}
