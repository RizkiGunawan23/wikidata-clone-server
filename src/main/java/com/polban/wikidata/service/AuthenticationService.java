package com.polban.wikidata.service;

import com.polban.wikidata.dto.request.user.authentication.UserSignInRequest;
import com.polban.wikidata.dto.request.user.authentication.UserSignUpRequest;
import com.polban.wikidata.dto.response.user.authentication.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse signUp(UserSignUpRequest request);

    AuthenticationResponse signIn(UserSignInRequest request);
}
