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
@Table(name = "residuos")
public class Residuo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "residuo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private ActividadesDiarias actividad;

    private String tipo;

    @Column(name = "peso_kg")
    private BigDecimal pesoKg;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "reciclaje", nullable = false)
    private Boolean reciclaje = false;

    private LocalDate creadoEn;

    @ManyToOne
    @JoinColumn(name = "miembro_id")
    private MiembroFamiliar miembro;

}