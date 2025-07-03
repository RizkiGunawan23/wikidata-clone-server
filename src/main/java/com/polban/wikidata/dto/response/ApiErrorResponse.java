package com.polban.wikidata.dto.response;

import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, ArrayList<String>> errors;
}
