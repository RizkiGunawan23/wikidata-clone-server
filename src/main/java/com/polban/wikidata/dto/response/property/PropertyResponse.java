package com.polban.wikidata.dto.response.property;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.propertyinfo.PropertyInfoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyResponse {
    private String pId;
    private String datatype;
    private String status;
    private Set<PropertyInfoResponse> propertyInfos;
}
