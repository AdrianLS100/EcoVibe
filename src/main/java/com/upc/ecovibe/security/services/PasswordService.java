package com.upc.ecovibe.security.services;

import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PasswordService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public PasswordService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public Optional<String> requestReset(String email) {
        return userRepo.findByEmail(email).map(u -> {
            String token = UUID.randomUUID().toString();
            u.setResetToken(token);
            u.setResetExpiresAt(Instant.now().plus(Duration.ofMinutes(30)));
            userRepo.save(u);

            // Enviar correo real con el enlace: <FRONT>/reset?token=token
            System.out.println("[DEBUG] Reset link: /reset?token=" + token);

            return token;
        });
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepo.findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        Instant exp = user.getResetExpiresAt();
        if (exp == null || exp.isBefore(Instant.now())) {
            throw new IllegalArgumentException("Token expirado");
        }

        user.setPassword(encoder.encode(newPassword));
        // invalidar token
        user.setResetToken(null);
        user.setResetExpiresAt(null);
        userRepo.save(user);
    }
}
