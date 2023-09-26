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
    private String street;
    @Column(unique = true, nullable = false)
    private String streetCode;
    private String town;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    public Address(Long id, String street, String streetCode, String town) {
        this.id = id;
        this.street = street;
        this.streetCode = streetCode;
        this.town = town;
    }


}
