package com.polban.wikidata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polban.wikidata.model.Language;
import com.polban.wikidata.repository.LanguageRepository;
import com.polban.wikidata.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }
}
