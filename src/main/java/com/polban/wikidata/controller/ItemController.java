package com.polban.wikidata.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polban.wikidata.dto.request.item.CreateItemRequest;
import com.polban.wikidata.dto.response.common.ApiSuccessResponse;
import com.polban.wikidata.dto.response.item.ItemResponse;
import com.polban.wikidata.service.ItemService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/knowledge-items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ItemResponse>> createItem(
            @Valid @RequestBody CreateItemRequest request) {
        ItemResponse response = itemService.createItem(request);
        ApiSuccessResponse<ItemResponse> apiSuccessResponse = ApiSuccessResponse.<ItemResponse>builder()
                .message("Berhasil membuat item")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }

}
