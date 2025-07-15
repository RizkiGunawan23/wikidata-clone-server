package com.polban.wikidata.dto.response.iteminfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.language.LanguageResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemInfoResponse {
    private String id;
    private String value;
    private String valueType;
    private LanguageResponse language;
}
