package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.utilisateur.UtilisateurResponse;
import org.example.bibliotheque.entity.Compte;
import org.example.bibliotheque.entity.Etudiant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilisateurMapperTest {

    private final UtilisateurMapper mapper = new UtilisateurMapper();

    @Test
    void toResponse_shouldMapAllFieldsCorrectly() {
        Compte compte = mock(Compte.class);
        when(compte.getCautionDisponible()).thenReturn(50.0);

        Etudiant utilisateur = new Etudiant();
        utilisateur.setId(1L);
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Jean");
        utilisateur.setAdresse("1 rue de Paris");
        utilisateur.setEmail("jean.dupont@mail.com");
        utilisateur.setLogin("jdupont");
        utilisateur.setCompte(compte);

        UtilisateurResponse response = mapper.toResponse(utilisateur);

        assertEquals(1L, response.getId());
        assertEquals("Dupont", response.getNom());
        assertEquals("Jean", response.getPrenom());
        assertEquals("1 rue de Paris", response.getAdresse());
        assertEquals("jean.dupont@mail.com", response.getEmail());
        assertEquals("jdupont", response.getLogin());

        // ⚠️ ici important
        assertEquals("Etudiant", response.getTypeUtilisateur());

        assertEquals(50.0, response.getCautionDisponible());
    }

    @Test
    void toResponse_shouldHandleNullCompte() {
        Etudiant utilisateur = new Etudiant();
        utilisateur.setCompte(null);

        UtilisateurResponse response = mapper.toResponse(utilisateur);

        assertNull(response.getCautionDisponible());
        assertEquals("Etudiant", response.getTypeUtilisateur());
    }
}