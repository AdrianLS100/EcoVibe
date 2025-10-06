package com.upc.ecovibe.security.validators;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RolMiembroValidator {

    private boolean hasRole(String simple) {
        String full = "ROLE_" + simple.toUpperCase();
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null) return false;
        for (GrantedAuthority ga : a.getAuthorities()) {
            if (full.equalsIgnoreCase(ga.getAuthority())) return true;
        }
        return false;
    }

    public void validateMiembroRequiredOrForbidden(Long miembroId) {
        if (hasRole("FAMILIAR") && miembroId == null) {
            throw new AccessDeniedException("En cuenta FAMILIAR debes indicar 'miembroId'.");
        }
        if (hasRole("PERSONAL") && miembroId != null) {
            throw new IllegalArgumentException("En cuenta PERSONAL no debes enviar 'miembroId'.");
        }
    }
}
