package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.Residuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {

    List<Residuo> findByActividad_Id(Long actividadId);

    @Query("select coalesce(sum(r.pesoKg), 0) from Residuo r where r.actividad.id = :actividadId")
    BigDecimal sumPesoKgByActividadId(@Param("actividadId") Long actividadId);


    @Query("select coalesce(sum(r.pesoKg), 0) from Residuo r where r.actividad.id = :actividadId")
    BigDecimal sumPesoByActividad(@Param("actividadId") Long actividadId);

    long deleteByActividad_Id(Long actividadId);

    @Query("""
       select coalesce(sum(r.pesoKg), 0)
       from Residuo r
       where r.actividad.fecha = :fecha
         and r.miembro.familia.id = :familiaId
       """)
    BigDecimal sumPesoByFamiliaAndFecha(@Param("familiaId") Long familiaId,
                                        @Param("fecha") LocalDate fecha);

    @Query("""
   select coalesce(sum(r.pesoKg), 0)
   from Residuo r
   where r.actividad.institucion.id = :institucionId
     and r.actividad.fecha = :fecha
""")
    BigDecimal sumPesoByInstitucionAndFecha(@Param("institucionId") Long institucionId,
                                            @Param("fecha") LocalDate fecha);

}
