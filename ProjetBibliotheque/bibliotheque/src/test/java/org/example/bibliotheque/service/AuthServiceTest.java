package org.example.bibliotheque.service;

import org.example.bibliotheque.dto.auth.LoginRequest;
import org.example.bibliotheque.dto.auth.LoginResponse;
import org.example.bibliotheque.entity.Bibliothecaire;
import org.example.bibliotheque.entity.Particulier;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.exception.BusinessException;
import org.example.bibliotheque.repository.BibliothecaireRepository;
import org.example.bibliotheque.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private BibliothecaireRepository bibliothecaireRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfullyAsUtilisateur() {
        LoginRequest request = new LoginRequest();
        request.setLogin("alice");
        request.setMotDePasse("alice123");

        Utilisateur user = new Particulier();
        user.setLogin("alice");
        user.setMotDePasse("alice123");
        user.setPremiereConnexion(false);

        when(utilisateurRepository.findByLogin("alice"))
                .thenReturn(Optional.of(user));

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("alice", response.getLogin());
        assertEquals("UTILISATEUR", response.getRole());
    }

    @Test
    void shouldLoginSuccessfullyAsBibliothecaire() {
        LoginRequest request = new LoginRequest();
        request.setLogin("biblio");
        request.setMotDePasse("biblio123");

        Bibliothecaire bibliothecaire = new Bibliothecaire();
        bibliothecaire.setLogin("biblio");
        bibliothecaire.setMotDePasse("biblio123");
        bibliothecaire.setPremiereConnexion(false);

        when(utilisateurRepository.findByLogin("biblio"))
                .thenReturn(Optional.empty());

        when(bibliothecaireRepository.findByLogin("biblio"))
                .thenReturn(Optional.of(bibliothecaire));

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("biblio", response.getLogin());
        assertEquals("BIBLIOTHECAIRE", response.getRole());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setLogin("inconnu");
        request.setMotDePasse("test");

        when(utilisateurRepository.findByLogin("inconnu"))
                .thenReturn(Optional.empty());

        when(bibliothecaireRepository.findByLogin("inconnu"))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> authService.login(request));
    }

    @Test
    void shouldThrowWhenPasswordIsWrongForUtilisateur() {
        LoginRequest request = new LoginRequest();
        request.setLogin("alice");
        request.setMotDePasse("mauvais");

        Utilisateur user = new Particulier();
        user.setLogin("alice");
        user.setMotDePasse("alice123");
        user.setPremiereConnexion(false);

        when(utilisateurRepository.findByLogin("alice"))
                .thenReturn(Optional.of(user));

        assertThrows(BusinessException.class, () -> authService.login(request));
    }

    @Test
    void shouldThrowWhenPasswordIsWrongForBibliothecaire() {
        LoginRequest request = new LoginRequest();
        request.setLogin("biblio");
        request.setMotDePasse("mauvais");

        Bibliothecaire bibliothecaire = new Bibliothecaire();
        bibliothecaire.setLogin("biblio");
        bibliothecaire.setMotDePasse("biblio123");
        bibliothecaire.setPremiereConnexion(false);

        when(utilisateurRepository.findByLogin("biblio"))
                .thenReturn(Optional.empty());

        when(bibliothecaireRepository.findByLogin("biblio"))
                .thenReturn(Optional.of(bibliothecaire));

        assertThrows(BusinessException.class, () -> authService.login(request));
    }
}