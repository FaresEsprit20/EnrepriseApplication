package com.stage.teamb.models;

import com.stage.teamb.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.INTEGER)
public class Users implements UserDetails{

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
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", registrationNumber=" + registrationNumber +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", tel=" + tel +
                ", occupation='" + occupation + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

}
