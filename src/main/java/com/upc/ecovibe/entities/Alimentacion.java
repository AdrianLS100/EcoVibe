package com.upc.ecovibe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "alimentacion")
public class Alimentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alimentacion_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "actividad_id", nullable = false)
    private ActividadesDiarias actividad;

    @Column(name = "tipo_dieta")
    private String tipoDieta;

    private String frecuencia;

    private LocalDate creado_en;

    @ManyToOne
    @JoinColumn(name = "miembro_id")
    private MiembroFamiliar miembro;


}