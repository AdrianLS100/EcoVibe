package com.upc.ecovibe.entities;

import com.upc.ecovibe.security.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "instituciones")
public class InstitucionEducativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreInstitucion;

    @Column(nullable = false)
    private String distrito;

    @Column(nullable = false)
    private String campus;

    @Column(nullable = false)
    private Integer numeroEstudiantes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User owner;

    private LocalDateTime creadaEn = LocalDateTime.now();
}
