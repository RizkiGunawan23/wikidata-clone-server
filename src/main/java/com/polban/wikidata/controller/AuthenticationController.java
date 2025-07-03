package com.polban.wikidata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polban.wikidata.dto.request.user.authentication.UserSignInRequest;
import com.polban.wikidata.dto.request.user.authentication.UserSignUpRequest;
import com.polban.wikidata.dto.response.ApiSuccessResponse;
import com.polban.wikidata.dto.response.user.authentication.AuthenticationResponse;
import com.polban.wikidata.service.AuthenticationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiSuccessResponse<AuthenticationResponse>> signUp(
            @Valid @RequestBody UserSignUpRequest request) {
        AuthenticationResponse response = authenticationService.signUp(request);
        ApiSuccessResponse<AuthenticationResponse> apiSuccessResponse = ApiSuccessResponse
                .<AuthenticationResponse>builder()
                .message("Sign up berhasil")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccessResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiSuccessResponse<AuthenticationResponse>> signIn(
            @Valid @RequestBody UserSignInRequest request) {
        AuthenticationResponse response = authenticationService.signIn(request);
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
        AuthenticationResponse response = authenticationService.refreshToken(token);
        ApiSuccessResponse<AuthenticationResponse> apiSuccessResponse = ApiSuccessResponse
                .<AuthenticationResponse>builder()
                .message("Token berhasil diperbarui")
                .data(response)
                .build();
        return ResponseEntity.ok().body(apiSuccessResponse);
    }
}
