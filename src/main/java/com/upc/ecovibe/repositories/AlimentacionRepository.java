package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.Alimentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
    List<Alimentacion> findByActividad_Id(Long actividadId);

    Optional<Alimentacion> findTopByActividad_IdOrderByIdDesc(Long actividadId);

    @Query("""
       select lower(a.tipoDieta), count(a)
       from Alimentacion a
       where a.actividad.fecha = :fecha
         and a.miembro.familia.id = :familiaId
       group by lower(a.tipoDieta)
       """)
    List<Object[]> countAlimentacionByFamiliaFechaGroupTipo(@Param("familiaId") Long familiaId,
                                                            @Param("fecha") LocalDate fecha);


    @Query("""
   select lower(a.tipoDieta), count(a)
   from Alimentacion a
   where a.actividad.fecha = :fecha
     and a.actividad.institucion.id = :institucionId
   group by lower(a.tipoDieta)
""")
    List<Object[]> countAlimentacionByInstitucionFechaGroupTipo(@Param("institucionId") Long institucionId,
                                                                @Param("fecha") LocalDate fecha);

}
