    package com.upc.ecovibe.entities;

    import com.upc.ecovibe.security.entities.User;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;

    import java.time.Instant;
    import java.time.LocalDate;

    @Getter
    @Setter
    @Entity
    @Table(name = "actividades_diarias")
    public class ActividadesDiarias {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "actividad_id")
        private Long id;
        @ManyToOne
        @JoinColumn(name = "usuario_id")
        private User usuario;
        private LocalDate fecha;
        private String descripcion;
        private Instant creadoEn;

        @PrePersist
        void prePersist() {
            if (creadoEn == null) creadoEn = Instant.now();
        }

        @ManyToOne
        @JoinColumn(name = "familia_id")
        private Familia familia;

        @ManyToOne
        @JoinColumn(name = "miembro_id")
        private MiembroFamiliar miembro;

        @ManyToOne
        @JoinColumn(name = "institucion_id")
        private InstitucionEducativa institucion;
    }