package com.polban.wikidata.dto.response.admin.language;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminLanguageSeederResponse {
    private int totalLanguages;
}