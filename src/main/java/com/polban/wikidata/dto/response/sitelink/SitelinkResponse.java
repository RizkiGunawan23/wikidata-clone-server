package com.polban.wikidata.dto.response.sitelink;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.language.LanguageResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SitelinkResponse {
    private String id;
    private String site;
    private String title;
    private String url;
    private String badge;
    private LanguageResponse language;
}
