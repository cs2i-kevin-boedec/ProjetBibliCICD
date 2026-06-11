package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Enseignant;
import org.example.bibliotheque.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class EnseignantFactory implements UtilisateurFactory {
    @Override
    public Utilisateur create(CreateUtilisateurRequest request) {
        Enseignant enseignant = new Enseignant();
        enseignant.setNom(request.getNom());
        enseignant.setPrenom(request.getPrenom());
        enseignant.setAdresse(request.getAdresse());
        enseignant.setEmail(request.getEmail());
        enseignant.setLogin(request.getLogin());
        enseignant.setMotDePasse(request.getMotDePasse());
        enseignant.setDepartement(request.getDepartement());
        return enseignant;
    }
}