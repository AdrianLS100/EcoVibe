package com.upc.ecovibe.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    private SecurityUtils() {}

    public static boolean hasRole(String roleSimpleName) {
        String full = "ROLE_" + roleSimpleName.toUpperCase();
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null) return false;
        for (GrantedAuthority ga : a.getAuthorities()) {
            if (full.equalsIgnoreCase(ga.getAuthority())) return true;
        }
        return false;
    }

    public static String currentUsername() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return a != null ? a.getName() : null;
    }
}
