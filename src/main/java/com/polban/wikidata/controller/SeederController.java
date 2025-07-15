package com.polban.wikidata.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polban.wikidata.dto.response.common.ApiSuccessResponse;
import com.polban.wikidata.dto.response.common.SeederResponse;
import com.polban.wikidata.service.SeederService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/v1/seeder")
public class SeederController {
    @Autowired
    private SeederService seederService;

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<SeederResponse>> createSeederData() {
        SeederResponse seederResponse = seederService.createSeederData();
        ApiSuccessResponse<SeederResponse> apiSuccessResponse = ApiSuccessResponse.<SeederResponse>builder()
                .message("Seeder data created successfully")
                .data(seederResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }

}
