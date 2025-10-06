package com.upc.ecovibe.services;

import com.upc.ecovibe.security.dtos.InstitucionRegisterRequest;
import com.upc.ecovibe.security.dtos.InstitucionRegisterResponse;
import com.upc.ecovibe.entities.InstitucionEducativa;
import com.upc.ecovibe.interfaces.IInstitucionEducativaService;
import com.upc.ecovibe.repositories.InstitucionEducativaRepository;
import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstitucionEducativaService implements IInstitucionEducativaService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private InstitucionEducativaRepository instRepo;
    @Autowired
    private PasswordEncoder encoder;

    public InstitucionEducativaService(UserRepository u, InstitucionEducativaRepository i, PasswordEncoder e) {
        this.userRepo = u; this.instRepo = i; this.encoder = e;
    }

    @Override
    @Transactional
    public InstitucionRegisterResponse registrar(InstitucionRegisterRequest req) {
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setEmail(req.getEmail());
        u.setDistrito(req.getDistrito());
        u.setEmailverificado(false);
        userRepo.save(u);

        // 2) Institución enlazada
        var inst = new InstitucionEducativa();
        inst.setNombreInstitucion(req.getNombreInstitucion());
        inst.setDistrito(req.getDistrito());
        inst.setCampus(req.getCampus());
        inst.setNumeroEstudiantes(req.getNumeroEstudiantes());
        inst.setOwner(u);
        inst = instRepo.save(inst);

        // 3) Respuesta
        return InstitucionRegisterResponse.builder()
                .userId(u.getId())
                .institucionId(inst.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .emailVerificado(false)
                .verificationUrl("/api/verify/" + u.getId())
                .build();
    }
}
