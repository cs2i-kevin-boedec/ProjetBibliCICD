package org.example.bibliotheque.controller;

import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.mapper.EmpruntMapper;
import org.example.bibliotheque.service.RetardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetardControllerTest {

    @Mock
    private RetardService retardService;

    @Mock
    private EmpruntMapper empruntMapper;

    @InjectMocks
    private RetardController retardController;

    @Test
    void listRetards_shouldReturnMappedResponses() {
        Long utilisateurId = 1L;

        Emprunt emprunt1 = mock(Emprunt.class);
        Emprunt emprunt2 = mock(Emprunt.class);

        EmpruntResponse response1 = mock(EmpruntResponse.class);
        EmpruntResponse response2 = mock(EmpruntResponse.class);

        when(retardService.listRetards()).thenReturn(List.of(emprunt1, emprunt2));
        when(empruntMapper.toResponse(emprunt1)).thenReturn(response1);
        when(empruntMapper.toResponse(emprunt2)).thenReturn(response2);

        List<EmpruntResponse> result = retardController.listRetards();

        assertEquals(List.of(response1, response2), result);
        verify(retardService).listRetards();
        verify(empruntMapper).toResponse(emprunt1);
        verify(empruntMapper).toResponse(emprunt2);
        verifyNoMoreInteractions(retardService, empruntMapper);
    }

    @Test
    void envoyerRelances_shouldCallService() {
        retardController.envoyerRelances();

        verify(retardService).envoyerRelances();
        verifyNoMoreInteractions(retardService);
        verifyNoInteractions(empruntMapper);
    }
}