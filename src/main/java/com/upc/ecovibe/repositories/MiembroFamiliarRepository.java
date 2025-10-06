package com.upc.ecovibe.repositories;
import com.upc.ecovibe.entities.MiembroFamiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MiembroFamiliarRepository extends JpaRepository<MiembroFamiliar, Long> {
    List<MiembroFamiliar> findByFamilia_Id(Long familiaId);
    Optional<MiembroFamiliar> findByFamilia_IdAndNombreIgnoreCase(Long familiaId, String nombre);
}
