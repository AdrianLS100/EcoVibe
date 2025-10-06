package com.upc.ecovibe.security.controllers;

import com.upc.ecovibe.security.dtos.FamiliaRegisterRequest;
import com.upc.ecovibe.security.dtos.FamiliaRegisterResponse;
import com.upc.ecovibe.security.entities.Role;
import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.services.RegistroFamiliarService;
import com.upc.ecovibe.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private PasswordEncoder bcrypt;
    @Autowired private RegistroFamiliarService registroFamiliarService;

    @PostMapping("/register/personal")
    public ResponseEntity<User> registerPersonal(@RequestBody User req) {
        req.setId(null);
        req.setPassword(bcrypt.encode(req.getPassword()));
        req.setEmailverificado(false);
        req.getRoles().clear();
        User saved = userService.save(req);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/register/familiar")
    public ResponseEntity<FamiliaRegisterResponse> registerFamiliar(
            @Valid @RequestBody FamiliaRegisterRequest req) {
        var resp = registroFamiliarService.registrar(req);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/verify/{userId}")
    public ResponseEntity<String> verify(@PathVariable Long userId) {
        userService.verifyEmail(userId);
        return ResponseEntity.ok("Email verificado. Ya puedes usar la aplicación.");
    }

    @PostMapping("/rol")
    @PreAuthorize("hasRole('ADMIN')")
    public void createRol(@RequestBody Role rol) {
        userService.grabar(rol);
    }

    @PostMapping("/save/{user_id}/{rol_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> saveUseRol(@PathVariable("user_id") Long user_id,
                                              @PathVariable("rol_id") Long rol_id){
        return new ResponseEntity<Integer>(userService.insertUserRol(user_id, rol_id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

}
