package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransporteRepository extends JpaRepository<Transporte, Long> {

    List<Transporte> findByActividad_Id(Long actividadId);

    @Query("select coalesce(sum(t.distanciaKm), 0) from Transporte t where t.actividad.id = :actividadId")
    BigDecimal sumDistanciaKmByActividadId(@Param("actividadId") Long actividadId);

    @Query("select coalesce(sum(t.distanciaKm), 0) from Transporte t where t.actividad.id = :actividadId and lower(t.medio) = lower(:medio)")
    BigDecimal sumDistanciaByActividadAndMedio(@Param("actividadId") Long actividadId, @Param("medio") String medio);

    long deleteByActividad_Id(Long actividadId);

    @Query("""
       select coalesce(sum(t.distanciaKm), 0)
       from Transporte t
       where t.actividad.fecha = :fecha
         and t.miembro.familia.id = :familiaId
         and lower(t.medio) = lower(:medio)
       """)
    BigDecimal sumDistanciaByFamiliaFechaMedio(@Param("familiaId") Long familiaId,
                                               @Param("fecha") LocalDate fecha,
                                               @Param("medio") String medio);

    @Query("""
   select coalesce(sum(t.distanciaKm), 0)
   from Transporte t
   where t.actividad.institucion.id = :institucionId
     and t.actividad.fecha = :fecha
     and lower(t.medio) = lower(:medio)
""")
    BigDecimal sumDistanciaByInstitucionFechaMedio(@Param("institucionId") Long institucionId,
                                                   @Param("fecha") LocalDate fecha,
                                                   @Param("medio") String medio);
}
