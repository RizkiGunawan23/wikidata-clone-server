package com.polban.wikidata.dto.response.item;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.polban.wikidata.dto.response.iteminfo.ItemInfoResponse;
import com.polban.wikidata.dto.response.sitelink.SitelinkResponse;
import com.polban.wikidata.dto.response.statement.StatementResponse;
import com.polban.wikidata.dto.response.user.UserResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDetailResponse {
    private String qId;
    private Set<ItemInfoResponse> itemInfos;
    private Set<StatementResponse> statements;
    private Set<SitelinkResponse> sitelinks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse createdBy;
    private UserResponse lastModifiedBy;
}
