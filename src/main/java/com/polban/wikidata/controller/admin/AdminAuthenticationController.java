package com.polban.wikidata.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polban.wikidata.dto.request.admin.authentication.AdminSignInRequest;
import com.polban.wikidata.dto.request.admin.authentication.AdminSignUpRequest;
import com.polban.wikidata.dto.response.ApiSuccessResponse;
import com.polban.wikidata.dto.response.authentication.AuthenticationResponse;
import com.polban.wikidata.service.admin.authentication.AdminAuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/admin/auth")
public class AdminAuthenticationController {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiSuccessResponse<AuthenticationResponse>> signUp(
            @Valid @RequestBody AdminSignUpRequest request) {
        AuthenticationResponse response = adminAuthenticationService.signUp(request);
        ApiSuccessResponse<AuthenticationResponse> apiSuccessResponse = ApiSuccessResponse
                .<AuthenticationResponse>builder()
                .message("Sign up berhasil")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiSuccessResponse<AuthenticationResponse>> signIn(
            @Valid @RequestBody AdminSignInRequest request) {
        AuthenticationResponse response = adminAuthenticationService.signIn(request);
        ApiSuccessResponse<AuthenticationResponse> apiSuccessResponse = ApiSuccessResponse
                .<AuthenticationResponse>builder()
                .message("Sign in berhasil")
                .data(response)
                .build();
        return ResponseEntity.ok().body(apiSuccessResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiSuccessResponse<AuthenticationResponse>> refreshToken(
            @RequestHeader("Authorization") String refreshToken) {
        String token = refreshToken.substring(7);
        AuthenticationResponse response = adminAuthenticationService.refreshToken(token);
        ApiSuccessResponse<AuthenticationResponse> apiSuccessResponse = ApiSuccessResponse
                .<AuthenticationResponse>builder()
                .message("Token berhasil diperbarui")
                .data(response)
                .build();
        return ResponseEntity.ok().body(apiSuccessResponse);
    }
}
