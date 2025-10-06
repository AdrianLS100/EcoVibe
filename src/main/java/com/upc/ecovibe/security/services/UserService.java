package com.upc.ecovibe.security.services;


import com.upc.ecovibe.security.entities.Role;
import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.RoleRepository;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    @Transactional
    public User save(User user) { return userRepository.save(user); }

    @Transactional
    public void grabar(Role role) { roleRepository.save(role); }


    public Integer insertUserRol(Long user_id, Long rol_id) {
        Integer result = 0;
        userRepository.insertUserRol(user_id, rol_id);
        return 1;
    }

    @Transactional
    public void verifyEmail(Long userId) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setEmailverificado(true);
        userRepository.save(u);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

}
