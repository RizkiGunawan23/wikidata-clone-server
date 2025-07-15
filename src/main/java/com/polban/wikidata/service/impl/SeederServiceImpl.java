package com.polban.wikidata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polban.wikidata.dto.response.authentication.AuthenticationResponse;
import com.polban.wikidata.dto.response.authentication.TokenData;
import com.polban.wikidata.dto.response.authentication.UserData;
import com.polban.wikidata.dto.response.common.SeederResponse;
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
    private Driver driver;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LanguageRepository languageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(SeederServiceImpl.class);

    @Override
    @Transactional
    public SeederResponse createSeederData() {
        createConstraints();

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

    private void createConstraints() {
        try (Session session = driver.session()) {
            // User constraints
            session.run("CREATE CONSTRAINT username_unique IF NOT EXISTS FOR (u:User) REQUIRE u.username IS UNIQUE");
            session.run("CREATE CONSTRAINT email_unique IF NOT EXISTS FOR (u:User) REQUIRE u.email IS UNIQUE");
            session.run("CREATE CONSTRAINT user_id_unique IF NOT EXISTS FOR (u:User) REQUIRE u.id IS UNIQUE");

            // Language constraints
            session.run(
                    "CREATE CONSTRAINT language_code_unique IF NOT EXISTS FOR (l:Language) REQUIRE l.code IS UNIQUE");

            // Item constraints
            session.run("CREATE CONSTRAINT item_qid_unique IF NOT EXISTS FOR (i:Item) REQUIRE i.qId IS UNIQUE");

            // ItemInfo constraints
            session.run("CREATE CONSTRAINT iteminfo_id_unique IF NOT EXISTS FOR (ii:ItemInfo) REQUIRE ii.id IS UNIQUE");

            // Property constraints
            session.run("CREATE CONSTRAINT property_pid_unique IF NOT EXISTS FOR (p:Property) REQUIRE p.pId IS UNIQUE");

            // PropertyInfo constraints
            session.run(
                    "CREATE CONSTRAINT propertyinfo_id_unique IF NOT EXISTS FOR (pi:PropertyInfo) REQUIRE pi.id IS UNIQUE");

            // Qualifier constraints
            session.run("CREATE CONSTRAINT qualifier_id_unique IF NOT EXISTS FOR (q:Qualifier) REQUIRE q.id IS UNIQUE");

            // Reference constraints
            session.run("CREATE CONSTRAINT reference_id_unique IF NOT EXISTS FOR (r:Reference) REQUIRE r.id IS UNIQUE");

            // ReferenceProperty constraints
            session.run(
                    "CREATE CONSTRAINT referenceproperty_id_unique IF NOT EXISTS FOR (rp:ReferenceProperty) REQUIRE rp.id IS UNIQUE");

            // Statement constraints
            session.run("CREATE CONSTRAINT statement_id_unique IF NOT EXISTS FOR (s:Statement) REQUIRE s.id IS UNIQUE");

            // Sitelink constraints
            session.run("CREATE CONSTRAINT sitelink_id_unique IF NOT EXISTS FOR (sl:Sitelink) REQUIRE sl.id IS UNIQUE");

            logger.info("Database constraints created successfully");
        } catch (Exception e) {
            logger.error("Failed to create constraints: ", e.getMessage());
        }
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
