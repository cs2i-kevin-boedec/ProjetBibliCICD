package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Particulier;
import org.example.bibliotheque.entity.Utilisateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticulierFactoryTest {

    private final ParticulierFactory factory = new ParticulierFactory();

    @Test
    void shouldCreateParticulier() {
        CreateUtilisateurRequest request = new CreateUtilisateurRequest();
        request.setNom("Bernard");
        request.setPrenom("Julie");
        request.setAdresse("Vannes");
        request.setEmail("julie@test.fr");
        request.setLogin("julie");
        request.setMotDePasse("julie123");

        Utilisateur result = factory.create(request);

        assertInstanceOf(Particulier.class, result);
        Particulier particulier = (Particulier) result;

        assertEquals("Bernard", particulier.getNom());
        assertEquals("Julie", particulier.getPrenom());
        assertEquals("Vannes", particulier.getAdresse());
        assertEquals("julie@test.fr", particulier.getEmail());
        assertEquals("julie", particulier.getLogin());
        assertEquals("julie123", particulier.getMotDePasse());
    }
}