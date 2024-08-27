package org.max.imageprocessingservice.service.auth_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.max.imageprocessingservice.dto.SignInRequest;
import org.max.imageprocessingservice.dto.SignUpRequest;
import org.max.imageprocessingservice.dto.AuthResponse;
import org.max.imageprocessingservice.entity.Role;
import org.max.imageprocessingservice.entity.User;
import org.max.imageprocessingservice.exception.AuthFailedException;
import org.max.imageprocessingservice.exception.UserAlreadyExists;
import org.max.imageprocessingservice.exception.UserNotFoundException;
import org.max.imageprocessingservice.repository.UserRepository;
import org.max.imageprocessingservice.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            log.error("User {} already exists", request.getUsername());
            throw new UserAlreadyExists("User Already Exists", HttpStatus.FORBIDDEN.value());
        }
        User newUser = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.User)
                .name(request.getName())
                .build();
        userRepository.save(newUser);
        String token = jwtUtil.generateToken(newUser);
        return AuthResponse
                .builder()
                .message("Sign Up Successful")
                .name(newUser.getName())
                .username(newUser.getUsername())
                .statusCode(HttpStatus.CREATED.value())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            log.error("User {} doesn't exist", request.getUsername());
            throw new UserNotFoundException("User doesn't Exists", HttpStatus.FORBIDDEN.value());
        }
        User verifiedUser = user.get();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(verifiedUser);
                log.info("Authentication success");
                return  AuthResponse
                        .builder()
                        .message("Sign In Successful")
                        .name(verifiedUser.getName())
                        .username(verifiedUser.getUsername())
                        .statusCode(HttpStatus.OK.value())
                        .token(token)
                        .build();
            }
        } catch (Exception e) {
            log.error("Authentication failed", e);
        }
        throw new AuthFailedException("Sign in failed", HttpStatus.FORBIDDEN.value());
    }
}
