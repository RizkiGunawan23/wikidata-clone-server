package com.polban.wikidata.dto.response.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private UserData user;
    private TokenData tokens;
}
