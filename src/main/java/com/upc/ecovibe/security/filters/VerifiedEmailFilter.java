package com.upc.ecovibe.security.filters;

import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class VerifiedEmailFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> WHITELIST = List.of(
            "/api/authenticate",
            "/api/verify/**"
    );

    public VerifiedEmailFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean isWhitelisted(String path) {
        for (String p : WHITELIST) if (pathMatcher.match(p, path)) return true;
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isWhitelisted(path)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }

        String username = auth.getName();
        User u = userRepository.findByUsername(username).orElse(null);

        if (u != null && Boolean.TRUE.equals(u.getEmailverificado())) {
            chain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Debes verificar tu email para continuar.\"}");
        }
    }
}
