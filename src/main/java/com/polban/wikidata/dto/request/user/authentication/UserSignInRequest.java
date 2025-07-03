package com.polban.wikidata.dto.request.user.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignInRequest {
    @NotBlank(message = "Username harus diisi")
    private String username;
    @NotBlank(message = "Password harus diisi")
    private String password;
}
