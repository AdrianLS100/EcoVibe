// com.upc.ecovibe.security.entities.User
package com.upc.ecovibe.security.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "users")
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private Integer edad;

    @Column(nullable = false)
    private String distrito;

    @Column(nullable = false)
    private Boolean emailverificado = false;

    @JsonIgnore
    @Column
    private String resetToken;

    @JsonIgnore
    @Column
    private java.time.Instant resetExpiresAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
