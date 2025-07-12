package com.polban.wikidata.service.admin.authentication.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.polban.wikidata.dto.request.admin.authentication.AdminSignInRequest;
import com.polban.wikidata.dto.request.admin.authentication.AdminSignUpRequest;
import com.polban.wikidata.dto.response.authentication.AuthenticationResponse;
import com.polban.wikidata.dto.response.authentication.TokenData;
import com.polban.wikidata.dto.response.authentication.UserData;
import com.polban.wikidata.exception.BadRequestException;
import com.polban.wikidata.exception.ConflictException;
import com.polban.wikidata.exception.UnauthorizedException;
import com.polban.wikidata.model.User;
import com.polban.wikidata.repository.UserRepository;
import com.polban.wikidata.security.JwtUtils;
import com.polban.wikidata.service.admin.authentication.AdminAuthenticationService;

@Service
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtUtils jwtUtils;

        @Override
        public AuthenticationResponse signUp(AdminSignUpRequest request) {
                if (userRepository.existsByUsername(request.getUsername()))
                        throw new ConflictException("Username sudah digunakan");

                if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
                        if (userRepository.existsByEmail(request.getEmail()))
                                throw new ConflictException("Email sudah digunakan");
                }

                User user = User.builder()
                                .id(UUID.randomUUID().toString())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail())
                                .role(User.ROLE_USER)
                                .build();

                user = userRepository.save(user);

                TokenData tokens = jwtUtils.generateTokens(user.getUsername(), user.getRole());

                UserData userData = UserData.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build();

                return AuthenticationResponse.builder()
                                .user(userData)
                                .tokens(tokens)
                                .build();
        }

        @Override
        public AuthenticationResponse signIn(AdminSignInRequest request) {
                User user = userRepository.findByUsername(request.getCredential())
                                .orElse(null);

                if (user == null)
                        user = userRepository.findByEmail(request.getCredential())
                                        .orElse(null);

                if (user == null)
                        throw new BadRequestException("Username/email atau password salah");

                if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                        throw new BadRequestException("Username/email atau password salah");

                TokenData tokens = jwtUtils.generateTokens(user.getUsername(), user.getRole());

                UserData userData = UserData.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build();

                return AuthenticationResponse.builder()
                                .user(userData)
                                .tokens(tokens)
                                .build();
        }

        @Override
        public AuthenticationResponse refreshToken(String refreshToken) {
                if (!jwtUtils.validateRefreshToken(refreshToken))
                        throw new UnauthorizedException("Refresh token tidak valid atau telah kedaluwarsa");

                String username = jwtUtils.getUsernameFromJwtToken(refreshToken);

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UnauthorizedException("Pengguna tidak ditemukan"));

                TokenData tokens = jwtUtils.generateTokens(user.getUsername(), user.getRole());

                UserData userData = UserData.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build();

                return AuthenticationResponse.builder()
                                .user(userData)
                                .tokens(tokens)
                                .build();
        }
}
