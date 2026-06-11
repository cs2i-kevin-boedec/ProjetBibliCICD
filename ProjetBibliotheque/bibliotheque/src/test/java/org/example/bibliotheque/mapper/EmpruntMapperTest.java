package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.entity.Exemplaire;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.enums.StatutEmprunt;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class EmpruntMapperTest {

    private final EmpruntMapper mapper = new EmpruntMapper();

    @Test
    void toResponse_shouldMapAllFieldsCorrectly() {
        // Arrange
        Utilisateur utilisateur = mock(Utilisateur.class);
        when(utilisateur.getPrenom()).thenReturn("Jean");
        when(utilisateur.getNom()).thenReturn("Dupont");

        Ouvrage ouvrage = mock(Ouvrage.class);
        when(ouvrage.getTitre()).thenReturn("Clean Code");

        Exemplaire exemplaire = mock(Exemplaire.class);
        when(exemplaire.getOuvrage()).thenReturn(ouvrage);
        when(exemplaire.getCodeBarre()).thenReturn("CB123");

        Emprunt emprunt = mock(Emprunt.class);
        when(emprunt.getId()).thenReturn(1L);
        when(emprunt.getUtilisateur()).thenReturn(utilisateur);
        when(emprunt.getExemplaire()).thenReturn(exemplaire);
        when(emprunt.getDateEmprunt()).thenReturn(LocalDate.now());
        when(emprunt.getDateRetourPrevue()).thenReturn(LocalDate.now().plusDays(7));
        when(emprunt.getDateRetourEffective()).thenReturn(null);
        when(emprunt.getStatut()).thenReturn(StatutEmprunt.EN_COURS);

        // Act
        EmpruntResponse response = mapper.toResponse(emprunt);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Jean Dupont", response.getUtilisateurNomComplet());
        assertEquals("Clean Code", response.getOuvrageTitre());
        assertEquals("CB123", response.getCodeBarreExemplaire());
        assertEquals(emprunt.getDateEmprunt(), response.getDateEmprunt());
        assertEquals(emprunt.getDateRetourPrevue(), response.getDateRetourPrevue());
        assertNull(response.getDateRetourEffective());
        assertEquals("EN_COURS", response.getStatut());
    }

    @Test
    void toResponse_shouldHandleNullStatut() {
        // Arrange
        Utilisateur utilisateur = mock(Utilisateur.class);
        when(utilisateur.getPrenom()).thenReturn("Jean");
        when(utilisateur.getNom()).thenReturn("Dupont");

        Ouvrage ouvrage = mock(Ouvrage.class);
        when(ouvrage.getTitre()).thenReturn("Clean Code");

        Exemplaire exemplaire = mock(Exemplaire.class);
        when(exemplaire.getOuvrage()).thenReturn(ouvrage);
        when(exemplaire.getCodeBarre()).thenReturn("CB123");

        Emprunt emprunt = mock(Emprunt.class);
        when(emprunt.getId()).thenReturn(1L);
        when(emprunt.getUtilisateur()).thenReturn(utilisateur);
        when(emprunt.getExemplaire()).thenReturn(exemplaire);
        when(emprunt.getDateEmprunt()).thenReturn(LocalDate.now());
        when(emprunt.getDateRetourPrevue()).thenReturn(LocalDate.now().plusDays(7));
        when(emprunt.getDateRetourEffective()).thenReturn(null);
        when(emprunt.getStatut()).thenReturn(null);

        // Act
        EmpruntResponse response = mapper.toResponse(emprunt);

        // Assert
        assertNull(response.getStatut());
    }
}