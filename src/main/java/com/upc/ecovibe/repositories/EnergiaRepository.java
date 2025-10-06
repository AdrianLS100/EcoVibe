package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.Energia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EnergiaRepository extends JpaRepository<Energia, Long> {

    List<Energia> findByActividad_Id(Long actividadId);

    @Query("select coalesce(sum(e.consumo), 0) from Energia e where e.actividad.id = :actividadId")
    BigDecimal sumConsumoByActividadId(@Param("actividadId") Long actividadId);

    @Query("select coalesce(sum(e.consumo), 0) from Energia e where e.actividad.id = :actividadId and lower(e.tipo) = lower(:tipo)")
    BigDecimal sumConsumoByActividadAndTipo(@Param("actividadId") Long actividadId, @Param("tipo") String tipo);

    long deleteByActividad_Id(Long actividadId);

    @Query("""
       select coalesce(sum(e.consumo), 0)
       from Energia e
       where e.actividad.fecha = :fecha
         and e.miembro.familia.id = :familiaId
         and lower(e.tipo) = lower(:tipo)
       """)
    BigDecimal sumConsumoByFamiliaFechaAndTipo(@Param("familiaId") Long familiaId,
                                               @Param("fecha") LocalDate fecha,
                                               @Param("tipo") String tipo);

    @Query("""
   select coalesce(sum(e.consumo), 0)
   from Energia e
   where e.actividad.institucion.id = :institucionId
     and e.actividad.fecha = :fecha
     and lower(e.tipo) = lower(:tipo)
""")
    BigDecimal sumConsumoByInstitucionFechaAndTipo(@Param("institucionId") Long institucionId,
                                                   @Param("fecha") LocalDate fecha,
                                                   @Param("tipo") String tipo);
}
