package com.polban.wikidata.dto.response;

import com.polban.wikidata.dto.response.authentication.AuthenticationResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeederResponse {
    private int totalLanguages;
    private AuthenticationResponse userData;
}
