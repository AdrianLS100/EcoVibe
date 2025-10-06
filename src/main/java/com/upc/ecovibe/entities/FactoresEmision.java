package com.upc.ecovibe.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "factores_emision")
public class FactoresEmision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factor_id")
    private Long id;

    private String categoria;

    private String subcategoria;

    @Column(name = "unidad_base")
    private String unidadBase;

    @Column(name = "factor_kgco2e_por_unidad")
    private BigDecimal factorKgco2ePerUnidad;

    private String fuente;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "vigente", nullable = false)
    private Boolean vigente = false;

    private Instant creadoEn;

    @PrePersist
    void prePersist() {
        if (creadoEn == null) creadoEn = Instant.now();
    }

}