package com.polban.wikidata.dto.response;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {
    private HttpStatus statusCode;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, ArrayList<String>> errors;
}
