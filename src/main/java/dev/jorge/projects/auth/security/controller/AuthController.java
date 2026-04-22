package dev.jorge.projects.auth.security.controller;

import dev.jorge.projects.auth.security.dto.request.LoginRequest;
import dev.jorge.projects.auth.security.dto.response.LoginResponse;

import dev.jorge.projects.auth.security.dto.request.RegisterUserRequest;
import dev.jorge.projects.auth.security.dto.response.RegisterUserResponse;
import dev.jorge.projects.auth.user.entity.User;
import dev.jorge.projects.auth.security.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.signIn(request.email(), request.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request){
        User newUser = authService.signUp(request.firstName(), request.lastName(), request.username(),request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(RegisterUserResponse.fromEntity(newUser));
    }

}
