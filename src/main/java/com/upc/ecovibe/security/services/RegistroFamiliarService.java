// services/RegistroFamiliarService.java
package com.upc.ecovibe.security.services;

import com.upc.ecovibe.dtos.FamiliaDTO;
import com.upc.ecovibe.entities.Familia;
import com.upc.ecovibe.repositories.FamiliaRepository;
import com.upc.ecovibe.security.dtos.FamiliaRegisterRequest;
import com.upc.ecovibe.security.dtos.FamiliaRegisterResponse;
import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroFamiliarService {

    private final UserRepository userRepo;
    private final FamiliaRepository familiaRepo;
    private final PasswordEncoder bcrypt;

    public RegistroFamiliarService(UserRepository userRepo, FamiliaRepository familiaRepo, PasswordEncoder bcrypt) {
        this.userRepo = userRepo;
        this.familiaRepo = familiaRepo;
        this.bcrypt = bcrypt;
    }

    @Transactional
    public FamiliaRegisterResponse registrar(FamiliaRegisterRequest req) {
        // 1) Crear usuario sin rol y con emailverificado=false
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(bcrypt.encode(req.getPassword()));
        u.setEmail(req.getEmail());
        u.setEdad(req.getEdad());
        u.setDistrito(req.getDistrito());
        u.setEmailverificado(false);
        u = userRepo.save(u);

        // 2) Crear familia del usuario owner con numeroMiembros
        Familia fam = new Familia();
        fam.setNombre("Familia " + req.getApellido());
        fam.setOwner(u);
        fam.setNumeroMiembros(req.getNumeroMiembros()); // <-- campo en entidad Familia
        fam = familiaRepo.save(fam);

        // 3) Respuesta
        return FamiliaRegisterResponse.builder()
                .userId(u.getId())
                .familiaId(fam.getId())
                .numeroMiembros(fam.getNumeroMiembros())
                .emailVerificado(false)
                .verificationUrl("/api/verify/" + u.getId())
                .build();
    }
}
