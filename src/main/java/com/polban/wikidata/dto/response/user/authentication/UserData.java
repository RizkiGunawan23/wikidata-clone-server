package com.polban.wikidata.dto.response.user.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private String id;
    private String email;
    private String username;
    private String role;
}
