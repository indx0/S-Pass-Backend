package com.indexzero.finals.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "is_enabled", nullable = false)
    Boolean isQREnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Authority> authorities;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<Entrance> entrances;

    @Override
    public String getUsername() {
        return this.login;
    }

}
