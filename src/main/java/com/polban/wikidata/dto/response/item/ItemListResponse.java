package com.polban.wikidata.dto.response.item;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemListResponse {
    private Set<ItemSummaryResponse> items;
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
