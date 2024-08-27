package org.max.imageprocessingservice.service.auth_service;

import org.max.imageprocessingservice.dto.SignInRequest;
import org.max.imageprocessingservice.dto.SignUpRequest;
import org.max.imageprocessingservice.dto.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);
}
