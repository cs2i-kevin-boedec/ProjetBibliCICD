package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Enseignant;
import org.example.bibliotheque.entity.Utilisateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnseignantFactoryTest {

    private final EnseignantFactory factory = new EnseignantFactory();

    @Test
    void shouldCreateEnseignant() {
        CreateUtilisateurRequest request = new CreateUtilisateurRequest();
        request.setNom("Martin");
        request.setPrenom("Paul");
        request.setAdresse("Rennes");
        request.setEmail("paul@test.fr");
        request.setLogin("pmartin");
        request.setMotDePasse("paul123");
        request.setDepartement("Informatique");

        Utilisateur result = factory.create(request);

        assertInstanceOf(Enseignant.class, result);
        Enseignant enseignant = (Enseignant) result;

        assertEquals("Martin", enseignant.getNom());
        assertEquals("Paul", enseignant.getPrenom());
        assertEquals("Rennes", enseignant.getAdresse());
        assertEquals("paul@test.fr", enseignant.getEmail());
        assertEquals("pmartin", enseignant.getLogin());
        assertEquals("paul123", enseignant.getMotDePasse());
        assertEquals("Informatique", enseignant.getDepartement());
    }
}