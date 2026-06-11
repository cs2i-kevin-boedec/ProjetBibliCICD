package org.example.bibliotheque.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String ancienMotDePasse;

    @NotBlank
    private String nouveauMotDePasse;
}
