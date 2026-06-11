package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Etudiant;
import org.example.bibliotheque.entity.Utilisateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantFactoryTest {

    private final EtudiantFactory factory = new EtudiantFactory();

    @Test
    void shouldCreateEtudiant() {
        CreateUtilisateurRequest request = new CreateUtilisateurRequest();
        request.setNom("Dupont");
        request.setPrenom("Alice");
        request.setAdresse("Lorient");
        request.setEmail("alice@test.fr");
        request.setLogin("alice");
        request.setMotDePasse("alice123");
        request.setAnneeUniversitaire(3);

        Utilisateur result = factory.create(request);

        assertInstanceOf(Etudiant.class, result);
        Etudiant etudiant = (Etudiant) result;

        assertEquals("Dupont", etudiant.getNom());
        assertEquals("Alice", etudiant.getPrenom());
        assertEquals("Lorient", etudiant.getAdresse());
        assertEquals("alice@test.fr", etudiant.getEmail());
        assertEquals("alice", etudiant.getLogin());
        assertEquals("alice123", etudiant.getMotDePasse());
        assertEquals(3, etudiant.getAnneeUniversitaire());
    }
}