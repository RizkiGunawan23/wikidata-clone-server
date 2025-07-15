package com.polban.wikidata.dto.response.item;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.iteminfo.ItemInfoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemSummaryResponse {
    private String qId;
    private Set<ItemInfoResponse> itemInfos;
}
