package com.polban.wikidata.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiSuccessResponse<T> {
    private String message;
    private T data;
}
