package com.upc.ecovibe.security.repositories;
import com.upc.ecovibe.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
