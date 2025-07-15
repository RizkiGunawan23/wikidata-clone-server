package com.polban.wikidata.dto.response.language;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageDetailResponse {
    private String code;
    private String name;
    private String variant;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
