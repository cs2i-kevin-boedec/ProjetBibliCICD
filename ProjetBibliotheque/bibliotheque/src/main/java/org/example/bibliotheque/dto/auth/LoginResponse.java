package org.example.bibliotheque.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String message;
    private boolean premiereConnexion;
    private String login;
    private String role;
}