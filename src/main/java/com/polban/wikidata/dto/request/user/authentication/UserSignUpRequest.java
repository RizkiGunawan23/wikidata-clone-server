package com.polban.wikidata.dto.request.user.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpRequest {
    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 6, max = 20, message = "Password harus antara 6 hingga 20 karakter")
    private String password;

    @Email(message = "Email tidak valid")
    private String email;
}
