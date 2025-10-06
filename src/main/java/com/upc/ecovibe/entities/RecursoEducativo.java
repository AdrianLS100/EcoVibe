package com.upc.ecovibe.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "recursos_educativos")
public class RecursoEducativo {

    public enum Tipo { ARTICULO, VIDEO, PODCAST }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String tema;

    @Column
    private String fuente;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private Tipo tipo;

    @Column(nullable = false)
    private String idioma;

    @Column(nullable = false)
    private String url;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}
