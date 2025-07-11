package com.polban.wikidata.service.admin.language.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polban.wikidata.dto.response.admin.language.AdminLanguageSeederResponse;
import com.polban.wikidata.exception.BadRequestException;
import com.polban.wikidata.model.Language;
import com.polban.wikidata.repository.LanguageRepository;
import com.polban.wikidata.service.admin.language.AdminLanguageService;

@Service
public class AdminLanguageServiceImpl implements AdminLanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public AdminLanguageSeederResponse createLanguageSeeder() {
        if (languageRepository.count() > 0) {
            throw new BadRequestException("Language seeder already exists");
        }

        try {
            InputStream inputStream = new ClassPathResource("language.json").getInputStream();
            List<Language> languages = objectMapper.readValue(inputStream, new TypeReference<List<Language>>() {
            });
            languageRepository.saveAll(languages);

            return AdminLanguageSeederResponse.builder()
                    .totalLanguages(languages.size())
                    .build();
        } catch (IOException e) {
            throw new Error("Failed to read language seeder file", e);
        }
    }
}
