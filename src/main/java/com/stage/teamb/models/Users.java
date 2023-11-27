package com.stage.teamb.models;

import com.stage.teamb.config.security.token.Token;
import com.stage.teamb.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Data
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails, Serializable {

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
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}
