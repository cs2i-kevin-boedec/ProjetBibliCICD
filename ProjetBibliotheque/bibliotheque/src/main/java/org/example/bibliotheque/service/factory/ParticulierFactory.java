package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Particulier;
import org.example.bibliotheque.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class ParticulierFactory implements UtilisateurFactory {
    @Override
    public Utilisateur create(CreateUtilisateurRequest request) {
        Particulier particulier = new Particulier();
        particulier.setNom(request.getNom());
        particulier.setPrenom(request.getPrenom());
        particulier.setAdresse(request.getAdresse());
        particulier.setEmail(request.getEmail());
        particulier.setLogin(request.getLogin());
        particulier.setMotDePasse(request.getMotDePasse());
        return particulier;
    }
}