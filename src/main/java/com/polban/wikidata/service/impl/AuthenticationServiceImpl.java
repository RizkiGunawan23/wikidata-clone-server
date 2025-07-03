package com.polban.wikidata.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.polban.wikidata.dto.request.user.authentication.UserSignInRequest;
import com.polban.wikidata.dto.request.user.authentication.UserSignUpRequest;
import com.polban.wikidata.dto.response.user.authentication.AuthenticationResponse;
import com.polban.wikidata.dto.response.user.authentication.TokenData;
import com.polban.wikidata.dto.response.user.authentication.UserData;
import com.polban.wikidata.exception.ConflictException;
import com.polban.wikidata.exception.BadRequestException;
import com.polban.wikidata.model.User;
import com.polban.wikidata.repository.UserRepository;
import com.polban.wikidata.security.JwtUtils;
import com.polban.wikidata.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public AuthenticationResponse signUp(UserSignUpRequest request) {
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
                .build();

        return AuthenticationResponse.builder()
                .user(userData)
                .tokens(tokens)
                .build();
    }

    @Override
    public AuthenticationResponse signIn(UserSignInRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Username atau password salah"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadRequestException("Username atau password salah");

        TokenData tokens = jwtUtils.generateTokens(user.getUsername(), user.getRole());

        UserData userData = UserData.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        return AuthenticationResponse.builder()
                .user(userData)
                .tokens(tokens)
                .build();
    }
}
