package com.upc.ecovibe.security.filters;
import com.upc.ecovibe.security.repositories.UserRepository;
import com.upc.ecovibe.security.services.CustomUserDetailsService;
import com.upc.ecovibe.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtRequestFilter(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil,
                            UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    private boolean isWhitelisted(HttpServletRequest req) {
        String p = req.getRequestURI();
        return p.startsWith("/api/authenticate")
                || p.startsWith("/api/verify");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                // ---- establece autenticación
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                if (!isWhitelisted(request)) {

                    boolean isAdmin = userDetails.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                    if (!isAdmin) {
                        var userOpt = userRepository.findByUsername(username);
                        boolean verificado = userOpt.isPresent() && Boolean.TRUE.equals(userOpt.get().getEmailverificado());

                        if (!verificado) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"error\":\"Debes verificar tu email para continuar.\"}");
                            return;
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}
