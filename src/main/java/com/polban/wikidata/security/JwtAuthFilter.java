package com.polban.wikidata.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polban.wikidata.model.User;
import com.polban.wikidata.repository.UserRepository;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    private void writeErrorResponse(int statusCode, HttpServletResponse response, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(
                new java.util.HashMap<String, Object>() {
                    {
                        put("message", message);
                    }
                });
        response.getWriter().write(json);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String header = request.getHeader("Authorization");

        if (requestURI.contains("/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            if (!jwtUtils.validateJwtToken(token)) {
                writeErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response,
                        "Token tidak valid atau telah kedaluwarsa");
                return;
            }

            String username = jwtUtils.getUsernameFromJwtToken(token);
            String role = jwtUtils.getRoleFromJwtToken(token);

            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) {
                writeErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, "Pengguna tidak ditemukan");
                return;
            }

            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, Collections.singleton(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            writeErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, "Terjadi kesalahan pada server");
            return;
        }
        filterChain.doFilter(request, response);
    }
}