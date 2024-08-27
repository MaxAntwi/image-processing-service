package org.max.imageprocessingservice.controller;

import lombok.RequiredArgsConstructor;
import org.max.imageprocessingservice.dto.AuthResponse;
import org.max.imageprocessingservice.dto.SignInRequest;
import org.max.imageprocessingservice.dto.SignUpRequest;
import org.max.imageprocessingservice.service.auth_service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpRequest request) {
        return new ResponseEntity<>(authService.signUp(request), HttpStatus.CREATED);
    }

    @PostMapping("signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(authService.signIn(request), HttpStatus.OK);
    }
}
