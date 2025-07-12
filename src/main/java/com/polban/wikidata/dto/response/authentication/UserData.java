package com.polban.wikidata.dto.response.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserData {
    private String id;
    private String email;
    private String username;
    private String password;
    private String role;
}
