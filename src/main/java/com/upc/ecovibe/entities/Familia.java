package com.upc.ecovibe.entities;

import com.upc.ecovibe.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "familia")
public class Familia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "familia_id")
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @Column(name="numero_miembros")
    private Integer numeroMiembros;
}
