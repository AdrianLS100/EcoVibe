package com.upc.ecovibe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "historial_puntos")
public class HistorialPuntos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false, length = 60)
    private String codigoAccion;

    @Column(nullable = false)
    private Integer puntos;

    @Column(length = 200)
    private String detalle;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}
