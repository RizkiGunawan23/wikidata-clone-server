package com.polban.wikidata.dto.response.propertyinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.language.LanguageResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyInfoResponse {
    private String id;
    private String value;
    private String type;
    private LanguageResponse language;
}
