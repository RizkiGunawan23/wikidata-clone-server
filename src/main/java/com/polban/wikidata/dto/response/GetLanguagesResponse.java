package com.polban.wikidata.dto.response;

import java.util.List;

import com.polban.wikidata.model.Language;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetLanguagesResponse {
    private List<Language> languages;
}
