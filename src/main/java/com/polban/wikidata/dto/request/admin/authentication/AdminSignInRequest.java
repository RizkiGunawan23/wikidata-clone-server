package com.polban.wikidata.dto.request.admin.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminSignInRequest {
    @NotBlank(message = "Username/email harus diisi")
    private String credential;
    @NotBlank(message = "Password harus diisi")
    private String password;
}
