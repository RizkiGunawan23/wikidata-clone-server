package com.polban.wikidata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polban.wikidata.dto.response.SeederResponse;
import com.polban.wikidata.dto.response.authentication.AuthenticationResponse;
import com.polban.wikidata.dto.response.authentication.TokenData;
import com.polban.wikidata.dto.response.authentication.UserData;
import com.polban.wikidata.exception.BadRequestException;
import com.polban.wikidata.model.Language;
import com.polban.wikidata.model.User;
import com.polban.wikidata.repository.LanguageRepository;
import com.polban.wikidata.repository.UserRepository;
import com.polban.wikidata.security.JwtUtils;
import com.polban.wikidata.service.SeederService;

@Service
public class SeederServiceImpl implements SeederService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LanguageRepository languageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public SeederResponse createSeederData() {
        String plainPassword = "admin123";
        User user = createUserSeeder(plainPassword);

        TokenData tokenData = jwtUtils.generateTokens(user.getUsername(), user.getRole());
        UserData userData = UserData.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(plainPassword)
                .role(user.getRole())
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .tokens(tokenData)
                .user(userData)
                .build();

        int totalLanguages = createLanguageSeeder();

        SeederResponse seederResponse = SeederResponse.builder()
                .totalLanguages(totalLanguages)
                .userData(authenticationResponse)
                .build();

        return seederResponse;
    }

    private User createUserSeeder(String plainPassword) {
        if (userRepository.count() > 0) {
            throw new BadRequestException("User data already exists");
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("adminwikidata1")
                .password(passwordEncoder.encode(plainPassword))
                .email("adminwikidata1@gmail.com")
                .role(User.ROLE_ADMIN)
                .build();

        userRepository.save(user);

        return user;
    }

    private int createLanguageSeeder() {
        if (languageRepository.count() > 0) {
            throw new BadRequestException("Language data already exists");
        }

        try {
            InputStream inputStream = new ClassPathResource("language.json").getInputStream();
            List<Language> languages = objectMapper.readValue(inputStream, new TypeReference<List<Language>>() {
            });
            languageRepository.saveAll(languages);

            return languages.size();
        } catch (IOException e) {
            throw new Error("Failed to read language seeder file", e);
        }
    }
}
