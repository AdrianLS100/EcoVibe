package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.RecursoEducativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecursoEducativoRepository extends JpaRepository<RecursoEducativo, Long> {

    @Query("""
    SELECT r FROM RecursoEducativo r
    WHERE (:tipo IS NULL OR r.tipo = :tipo)
      AND (:pattern IS NULL OR LOWER(r.titulo) LIKE :pattern)
    ORDER BY r.creadoEn DESC
""")
    Page<RecursoEducativo> search(@Param("tipo") RecursoEducativo.Tipo tipo,
                                  @Param("pattern") String pattern,
                                  Pageable pageable);

    @Query("""
    SELECT r FROM RecursoEducativo r
    WHERE r.id <> :id
      AND (
           (:tema   IS NOT NULL AND LOWER(r.tema)   = LOWER(:tema)) OR
           (:tipo   IS NOT NULL AND r.tipo          = :tipo)        OR
           (:fuente IS NOT NULL AND LOWER(r.fuente) = LOWER(:fuente))
      )
    ORDER BY r.creadoEn DESC
""")
    Page<RecursoEducativo> relacionados(@Param("id") Long id,
                                        @Param("tema") String tema,
                                        @Param("tipo") RecursoEducativo.Tipo tipo,
                                        @Param("fuente") String fuente,
                                        Pageable pageable);
}
