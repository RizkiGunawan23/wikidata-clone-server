package com.polban.wikidata.service;

import com.polban.wikidata.dto.request.item.CreateItemRequest;
import com.polban.wikidata.dto.response.item.ItemResponse;

public interface ItemService {
    ItemResponse createItem(CreateItemRequest request);
}