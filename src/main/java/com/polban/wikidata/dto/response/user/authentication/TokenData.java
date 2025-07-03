package com.polban.wikidata.dto.response.user.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenData {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
}
