package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.FactoresEmision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FactoresEmisionRepository extends JpaRepository<FactoresEmision, Long> {

    Optional<FactoresEmision> findFirstByCategoriaIgnoreCaseAndSubcategoriaIgnoreCaseAndUnidadBaseIgnoreCaseAndVigenteTrue(
            String categoria, String subcategoria, String unidadBase
    );

    List<FactoresEmision> findByCategoriaIgnoreCaseAndVigenteTrue(String categoria);
}
