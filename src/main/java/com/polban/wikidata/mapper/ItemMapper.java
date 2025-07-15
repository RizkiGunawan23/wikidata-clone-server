package com.polban.wikidata.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.polban.wikidata.dto.response.item.ItemResponse;
import com.polban.wikidata.dto.response.iteminfo.ItemInfoResponse;
import com.polban.wikidata.dto.response.language.LanguageResponse;
import com.polban.wikidata.model.Item;
import com.polban.wikidata.model.ItemInfo;

@Component
public class ItemMapper {
    public ItemResponse toCreateResponse(Item item) {
        Set<ItemInfoResponse> itemInfoResponses = item.getItemInfos()
                .stream()
                .map(this::mapItemInfoToResponse)
                .collect(Collectors.toSet());

        return ItemResponse.builder()
                .qId(item.getQId())
                .itemInfos(itemInfoResponses)
                .statements(new HashSet<>())
                .sitelinks(new HashSet<>())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    private ItemInfoResponse mapItemInfoToResponse(ItemInfo itemInfo) {
        return ItemInfoResponse.builder()
                .id(itemInfo.getId())
                .value(itemInfo.getValue())
                .valueType(itemInfo.getValueType())
                .language(LanguageResponse.builder()
                        .code(itemInfo.getLanguage().getCode())
                        .name(itemInfo.getLanguage().getName())
                        .variant(itemInfo.getLanguage().getVariant())
                        .build())
                .build();
    }
}
