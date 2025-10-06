package com.upc.ecovibe.entities;

import com.upc.ecovibe.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "miembro_familiar",
        uniqueConstraints = @UniqueConstraint(columnNames = {"familia_id","nombre"}))
public class MiembroFamiliar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "miembro_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "familia_id")
    private Familia familia;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 60)
    private String relacion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
