//package com.stage.teamb.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicUpdate;
//
//import java.io.Serializable;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@DynamicUpdate
//@Builder
//public class Address implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String street;
//    @Column(unique = true, nullable = false)
//    private String streetCode;
//    private String town;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id", unique = true)
//    private Employee employee;
//
//    public Address(Long id, String street, String streetCode, String town) {
//        this.id = id;
//        this.street = street;
//        this.streetCode = streetCode;
//        this.town = town;
//    }
//
//    public void setEmployeeForAddress(Employee employee) {
//        this.employee = employee;
//    }
//
//    public void removeEmployeeFromAddress() {
//        this.employee = null;
//    }
//
//    @Override
//    public String toString() {
//        return "Address{" +
//                "id=" + id +
//                ", street='" + street + '\'' +
//                ", streetCode='" + streetCode + '\'' +
//                ", town='" + town + '\'' +
//                ", employee=" + (employee != null ? employee.getId() : null) +
//                '}';
//    }
//
//
//
//}
