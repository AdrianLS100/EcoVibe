package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.InstitucionEducativa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstitucionEducativaRepository extends JpaRepository<InstitucionEducativa, Long> {
    List<InstitucionEducativa> findByOwner_Id(Long ownerUserId);
}
