package org.example.bibliotheque.controller;

import org.example.bibliotheque.dto.emprunt.EmpruntRequest;
import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.mapper.EmpruntMapper;
import org.example.bibliotheque.service.EmpruntService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpruntControllerTest {

    @Mock
    private EmpruntService empruntService;

    @Mock
    private EmpruntMapper empruntMapper;

    @InjectMocks
    private EmpruntController empruntController;

    @Test
    void emprunter_shouldCallServiceAndMapper_andReturnResponse() {
        // Arrange
        EmpruntRequest request = mock(EmpruntRequest.class);
        Emprunt emprunt = mock(Emprunt.class);
        EmpruntResponse expectedResponse = mock(EmpruntResponse.class);

        when(empruntService.emprunter(request)).thenReturn(emprunt);
        when(empruntMapper.toResponse(emprunt)).thenReturn(expectedResponse);

        // Act
        EmpruntResponse actualResponse = empruntController.emprunter(request);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(empruntService, times(1)).emprunter(request);
        verify(empruntMapper, times(1)).toResponse(emprunt);
        verifyNoMoreInteractions(empruntService, empruntMapper);
    }

    @Test
    void listByUtilisateur_shouldReturnMappedResponses() {
        // Arrange
        Long utilisateurId = 1L;

        Emprunt emprunt1 = mock(Emprunt.class);
        Emprunt emprunt2 = mock(Emprunt.class);

        EmpruntResponse response1 = mock(EmpruntResponse.class);
        EmpruntResponse response2 = mock(EmpruntResponse.class);

        when(empruntService.listByUtilisateur(utilisateurId))
                .thenReturn(List.of(emprunt1, emprunt2));

        when(empruntMapper.toResponse(emprunt1)).thenReturn(response1);
        when(empruntMapper.toResponse(emprunt2)).thenReturn(response2);

        // Act
        List<EmpruntResponse> result = empruntController.listByUtilisateur(utilisateurId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(List.of(response1, response2), result);

        verify(empruntService, times(1)).listByUtilisateur(utilisateurId);
        verify(empruntMapper, times(1)).toResponse(emprunt1);
        verify(empruntMapper, times(1)).toResponse(emprunt2);
        verifyNoMoreInteractions(empruntService, empruntMapper);
    }

    @Test
    void listActifsByUtilisateur_shouldReturnMappedResponses() {
        // Arrange
        Long utilisateurId = 1L;

        Emprunt emprunt1 = mock(Emprunt.class);

        EmpruntResponse response1 = mock(EmpruntResponse.class);

        when(empruntService.findActifsByUtilisateur(utilisateurId))
                .thenReturn(List.of(emprunt1));

        when(empruntMapper.toResponse(emprunt1)).thenReturn(response1);

        // Act
        List<EmpruntResponse> result = empruntController.listActifsByUtilisateur(utilisateurId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(List.of(response1), result);

        verify(empruntService, times(1)).findActifsByUtilisateur(utilisateurId);
        verify(empruntMapper, times(1)).toResponse(emprunt1);
        verifyNoMoreInteractions(empruntService, empruntMapper);
    }
}