package org.example.bibliotheque.service;

import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.auth.ChangePasswordRequest;
import org.example.bibliotheque.dto.auth.LoginRequest;
import org.example.bibliotheque.dto.auth.LoginResponse;
import org.example.bibliotheque.entity.Bibliothecaire;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.exception.BusinessException;
import org.example.bibliotheque.repository.BibliothecaireRepository;
import org.example.bibliotheque.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final BibliothecaireRepository bibliothecaireRepository;

    public LoginResponse login(LoginRequest request) {
        return utilisateurRepository.findByLogin(request.getLogin())
                .map(utilisateur -> authenticateUtilisateur(utilisateur, request.getMotDePasse()))
                .orElseGet(() -> bibliothecaireRepository.findByLogin(request.getLogin())
                        .map(bibliothecaire -> authenticateBibliothecaire(bibliothecaire, request.getMotDePasse()))
                        .orElseThrow(() -> new BusinessException("Identifiants invalides")));
    }

    public void changePassword(ChangePasswordRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(request.getLogin()).orElse(null);

        if (utilisateur != null) {
            if (!utilisateur.getMotDePasse().equals(request.getAncienMotDePasse())) {
                throw new BusinessException("Ancien mot de passe incorrect");
            }

            utilisateur.setMotDePasse(request.getNouveauMotDePasse());
            utilisateur.setPremiereConnexion(false);
            utilisateurRepository.save(utilisateur);
            return;
        }

        Bibliothecaire bibliothecaire = bibliothecaireRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new BusinessException("Utilisateur introuvable"));

        if (!bibliothecaire.getMotDePasse().equals(request.getAncienMotDePasse())) {
            throw new BusinessException("Ancien mot de passe incorrect");
        }

        bibliothecaire.setMotDePasse(request.getNouveauMotDePasse());
        bibliothecaire.setPremiereConnexion(false);
        bibliothecaireRepository.save(bibliothecaire);
    }

    private LoginResponse authenticateUtilisateur(Utilisateur utilisateur, String motDePasse) {
        if (!utilisateur.getMotDePasse().equals(motDePasse)) {
            throw new BusinessException("Identifiants invalides");
        }

        return new LoginResponse(
                utilisateur.getId(),
                "Connexion réussie",
                utilisateur.isPremiereConnexion(),
                utilisateur.getLogin(),
                "UTILISATEUR"
        );
    }

    private LoginResponse authenticateBibliothecaire(Bibliothecaire bibliothecaire, String motDePasse) {
        if (!bibliothecaire.getMotDePasse().equals(motDePasse)) {
            throw new BusinessException("Identifiants invalides");
        }

        return new LoginResponse(
                bibliothecaire.getId(),
                "Connexion réussie",
                bibliothecaire.isPremiereConnexion(),
                bibliothecaire.getLogin(),
                "BIBLIOTHECAIRE"
        );
    }
}