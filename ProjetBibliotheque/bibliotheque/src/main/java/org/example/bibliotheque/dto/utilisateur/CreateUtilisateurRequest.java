package org.example.bibliotheque.dto.utilisateur;

import org.example.bibliotheque.enums.TypeUtilisateur;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUtilisateurRequest {
    @NotNull
    private TypeUtilisateur typeUtilisateur;
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String adresse;
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String motDePasse;
    @NotNull
    private Double cautionInitiale;

    private Integer anneeUniversitaire;
    private String departement;
}