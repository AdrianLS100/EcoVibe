package com.upc.ecovibe.repositories;
import com.upc.ecovibe.entities.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    List<Familia> findByOwner_Id(Long ownerUserId);
}
