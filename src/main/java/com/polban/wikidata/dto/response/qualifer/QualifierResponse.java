package com.polban.wikidata.dto.response.qualifer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.property.PropertyResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualifierResponse {
    private String id;
    private String value;
    private String valueType;
    private PropertyResponse property;
}
