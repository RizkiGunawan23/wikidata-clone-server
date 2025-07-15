package com.polban.wikidata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polban.wikidata.dto.response.common.ApiSuccessResponse;
import com.polban.wikidata.model.Language;
import com.polban.wikidata.service.LanguageService;

@RestController
@RequestMapping("/api/v1/languages")
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<Language>>> getLanguages() {
        List<Language> languages = languageService.getLanguages();
        ApiSuccessResponse<List<Language>> apiSuccessResponse = ApiSuccessResponse.<List<Language>>builder()
                .message("Berhasil mendapatkan daftar bahasa")
                .data(languages)
                .build();
        return ResponseEntity.ok().body(apiSuccessResponse);
    }
}
