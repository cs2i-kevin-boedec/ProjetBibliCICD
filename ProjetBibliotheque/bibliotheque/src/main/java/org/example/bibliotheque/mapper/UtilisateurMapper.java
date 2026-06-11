package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.utilisateur.UtilisateurResponse;
import org.example.bibliotheque.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public UtilisateurResponse toResponse(Utilisateur utilisateur) {
        return UtilisateurResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .adresse(utilisateur.getAdresse())
                .email(utilisateur.getEmail())
                .login(utilisateur.getLogin())
                .typeUtilisateur(utilisateur.getClass().getSimpleName())
                .cautionDisponible(
                        utilisateur.getCompte() != null ? utilisateur.getCompte().getCautionDisponible() : null
                )
                .build();
    }
}