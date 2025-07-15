package com.polban.wikidata.dto.response.language;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageResponse {
    private String code;
    private String name;
    private String variant;
}
