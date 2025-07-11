package com.polban.wikidata.service.admin.authentication;

import com.polban.wikidata.dto.request.admin.authentication.AdminSignInRequest;
import com.polban.wikidata.dto.request.admin.authentication.AdminSignUpRequest;
import com.polban.wikidata.dto.response.authentication.AuthenticationResponse;

public interface AdminAuthenticationService {
    AuthenticationResponse signUp(AdminSignUpRequest request);

    AuthenticationResponse signIn(AdminSignInRequest request);

    AuthenticationResponse refreshToken(String refreshToken);
}
