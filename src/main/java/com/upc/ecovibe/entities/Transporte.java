package com.upc.ecovibe.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "transporte")
public class Transporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transporte_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private ActividadesDiarias actividad;

    private String medio;

    @Column(name = "distancia_km")
    private BigDecimal distanciaKm;

    private String frecuencia;

    private LocalDate creadoEn;

    @ManyToOne
    @JoinColumn(name = "miembro_id")
    private MiembroFamiliar miembro;

}