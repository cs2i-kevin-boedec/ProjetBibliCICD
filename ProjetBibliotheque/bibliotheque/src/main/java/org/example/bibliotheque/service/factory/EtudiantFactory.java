package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Etudiant;
import org.example.bibliotheque.entity.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class EtudiantFactory implements UtilisateurFactory {
    @Override
    public Utilisateur create(CreateUtilisateurRequest request) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(request.getNom());
        etudiant.setPrenom(request.getPrenom());
        etudiant.setAdresse(request.getAdresse());
        etudiant.setEmail(request.getEmail());
        etudiant.setLogin(request.getLogin());
        etudiant.setMotDePasse(request.getMotDePasse());
        etudiant.setAnneeUniversitaire(request.getAnneeUniversitaire());
        return etudiant;
    }
}