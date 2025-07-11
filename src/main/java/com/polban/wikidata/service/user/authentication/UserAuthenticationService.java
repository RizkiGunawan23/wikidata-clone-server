package com.polban.wikidata.service.user.authentication;

import com.polban.wikidata.dto.request.user.authentication.UserSignInRequest;
import com.polban.wikidata.dto.request.user.authentication.UserSignUpRequest;
import com.polban.wikidata.dto.response.authentication.AuthenticationResponse;

public interface UserAuthenticationService {
    AuthenticationResponse signUp(UserSignUpRequest request);

    AuthenticationResponse signIn(UserSignInRequest request);

    AuthenticationResponse refreshToken(String refreshToken);
}
