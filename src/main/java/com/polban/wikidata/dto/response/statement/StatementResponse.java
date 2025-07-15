package com.polban.wikidata.dto.response.statement;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.property.PropertyResponse;
import com.polban.wikidata.dto.response.qualifer.QualifierResponse;
import com.polban.wikidata.dto.response.reference.ReferenceResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatementResponse {
    private String id;
    private String rank;
    private String value;
    private String valueType;
    private PropertyResponse property;
    private Set<QualifierResponse> qualifiers;
    private Set<ReferenceResponse> references;
}
