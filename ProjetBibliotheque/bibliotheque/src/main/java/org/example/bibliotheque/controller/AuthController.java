package org.example.bibliotheque.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.auth.ChangePasswordRequest;
import org.example.bibliotheque.dto.auth.LoginRequest;
import org.example.bibliotheque.dto.auth.LoginResponse;
import org.example.bibliotheque.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/change-password")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
    }
}