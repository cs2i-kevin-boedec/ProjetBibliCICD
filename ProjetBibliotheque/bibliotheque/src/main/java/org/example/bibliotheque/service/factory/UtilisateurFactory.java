package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Utilisateur;

public interface UtilisateurFactory {
    Utilisateur create(CreateUtilisateurRequest request);
}