package org.example.bibliotheque.controller;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.dto.utilisateur.UtilisateurResponse;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.mapper.UtilisateurMapper;
import org.example.bibliotheque.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilisateurControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private UtilisateurMapper utilisateurMapper;

    @InjectMocks
    private UtilisateurController utilisateurController;

    @Test
    void create_shouldCallServiceAndMapper_andReturnResponse() {
        CreateUtilisateurRequest request = mock(CreateUtilisateurRequest.class);
        Utilisateur utilisateur = mock(Utilisateur.class);
        UtilisateurResponse response = mock(UtilisateurResponse.class);

        when(utilisateurService.create(request)).thenReturn(utilisateur);
        when(utilisateurMapper.toResponse(utilisateur)).thenReturn(response);

        UtilisateurResponse result = utilisateurController.create(request);

        assertSame(response, result);
        verify(utilisateurService).create(request);
        verify(utilisateurMapper).toResponse(utilisateur);
        verifyNoMoreInteractions(utilisateurService, utilisateurMapper);
    }

    @Test
    void findById_shouldReturnMappedResponse() {
        Long id = 1L;
        Utilisateur utilisateur = mock(Utilisateur.class);
        UtilisateurResponse response = mock(UtilisateurResponse.class);

        when(utilisateurService.findById(id)).thenReturn(utilisateur);
        when(utilisateurMapper.toResponse(utilisateur)).thenReturn(response);

        UtilisateurResponse result = utilisateurController.findById(id);

        assertSame(response, result);
        verify(utilisateurService).findById(id);
        verify(utilisateurMapper).toResponse(utilisateur);
        verifyNoMoreInteractions(utilisateurService, utilisateurMapper);
    }

    @Test
    void findAll_shouldReturnMappedResponses() {
        Utilisateur u1 = mock(Utilisateur.class);
        Utilisateur u2 = mock(Utilisateur.class);

        UtilisateurResponse r1 = mock(UtilisateurResponse.class);
        UtilisateurResponse r2 = mock(UtilisateurResponse.class);

        when(utilisateurService.findAll()).thenReturn(List.of(u1, u2));
        when(utilisateurMapper.toResponse(u1)).thenReturn(r1);
        when(utilisateurMapper.toResponse(u2)).thenReturn(r2);

        List<UtilisateurResponse> result = utilisateurController.findAll();

        assertEquals(List.of(r1, r2), result);
        verify(utilisateurService).findAll();
        verify(utilisateurMapper).toResponse(u1);
        verify(utilisateurMapper).toResponse(u2);
        verifyNoMoreInteractions(utilisateurService, utilisateurMapper);
    }

    @Test
    void findByLogin_shouldReturnMappedResponse() {
        String login = "testLogin";
        Utilisateur utilisateur = mock(Utilisateur.class);
        UtilisateurResponse response = mock(UtilisateurResponse.class);

        when(utilisateurService.findByLogin(login)).thenReturn(utilisateur);
        when(utilisateurMapper.toResponse(utilisateur)).thenReturn(response);

        UtilisateurResponse result = utilisateurController.findByLogin(login);

        assertSame(response, result);
        verify(utilisateurService).findByLogin(login);
        verify(utilisateurMapper).toResponse(utilisateur);
        verifyNoMoreInteractions(utilisateurService, utilisateurMapper);
    }

    @Test
    void search_shouldReturnMappedResponses() {
        String valeur = "john";

        Utilisateur u1 = mock(Utilisateur.class);
        UtilisateurResponse r1 = mock(UtilisateurResponse.class);

        when(utilisateurService.searchByNomOrPrenom(valeur)).thenReturn(List.of(u1));
        when(utilisateurMapper.toResponse(u1)).thenReturn(r1);

        List<UtilisateurResponse> result = utilisateurController.search(valeur);

        assertEquals(List.of(r1), result);
        verify(utilisateurService).searchByNomOrPrenom(valeur);
        verify(utilisateurMapper).toResponse(u1);
        verifyNoMoreInteractions(utilisateurService, utilisateurMapper);
    }
}