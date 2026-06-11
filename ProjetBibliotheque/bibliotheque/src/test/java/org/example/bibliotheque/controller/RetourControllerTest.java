package org.example.bibliotheque.controller;

import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.dto.retour.RetourRequest;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.mapper.EmpruntMapper;
import org.example.bibliotheque.service.RetourService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetourControllerTest {

    @Mock
    private RetourService retourService;

    @Mock
    private EmpruntMapper empruntMapper;

    @InjectMocks
    private RetourController retourController;

    @Test
    void retourner_shouldCallServiceAndMapper_andReturnResponse() {
        RetourRequest request = mock(RetourRequest.class);
        Emprunt emprunt = mock(Emprunt.class);
        EmpruntResponse expectedResponse = mock(EmpruntResponse.class);

        when(retourService.retourner(request)).thenReturn(emprunt);
        when(empruntMapper.toResponse(emprunt)).thenReturn(expectedResponse);

        EmpruntResponse actualResponse = retourController.retourner(request);

        assertSame(expectedResponse, actualResponse);
        verify(retourService).retourner(request);
        verify(empruntMapper).toResponse(emprunt);
        verifyNoMoreInteractions(retourService, empruntMapper);
    }
}