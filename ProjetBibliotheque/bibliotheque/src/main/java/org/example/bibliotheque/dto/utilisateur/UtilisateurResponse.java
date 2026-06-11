package org.example.bibliotheque.dto.utilisateur;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UtilisateurResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String login;
    private String typeUtilisateur;
    private Double cautionDisponible;
}