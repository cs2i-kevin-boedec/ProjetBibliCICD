package org.example.bibliotheque.controller;

import org.example.bibliotheque.dto.ouvrage.LivreCreateRequest;
import org.example.bibliotheque.dto.ouvrage.OuvrageResponse;
import org.example.bibliotheque.dto.ouvrage.OuvrageSearchRequest;
import org.example.bibliotheque.dto.ouvrage.RevueCreateRequest;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.mapper.OuvrageMapper;
import org.example.bibliotheque.service.OuvrageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OuvrageControllerTest {

    @Mock
    private OuvrageService ouvrageService;

    @Mock
    private OuvrageMapper ouvrageMapper;

    @InjectMocks
    private OuvrageController ouvrageController;

    @Test
    void createLivre_shouldCallServiceAndMapper_andReturnResponse() {
        LivreCreateRequest request = mock(LivreCreateRequest.class);
        Ouvrage ouvrage = mock(Ouvrage.class);
        OuvrageResponse response = mock(OuvrageResponse.class);

        when(ouvrageService.createLivre(request)).thenReturn(ouvrage);
        when(ouvrageMapper.toResponse(ouvrage)).thenReturn(response);

        OuvrageResponse result = ouvrageController.createLivre(request);

        assertEquals(response, result);
        verify(ouvrageService).createLivre(request);
        verify(ouvrageMapper).toResponse(ouvrage);
        verifyNoMoreInteractions(ouvrageService, ouvrageMapper);
    }

    @Test
    void createRevue_shouldCallServiceAndMapper_andReturnResponse() {
        RevueCreateRequest request = mock(RevueCreateRequest.class);
        Ouvrage ouvrage = mock(Ouvrage.class);
        OuvrageResponse response = mock(OuvrageResponse.class);

        when(ouvrageService.createRevue(request)).thenReturn(ouvrage);
        when(ouvrageMapper.toResponse(ouvrage)).thenReturn(response);

        OuvrageResponse result = ouvrageController.createRevue(request);

        assertEquals(response, result);
        verify(ouvrageService).createRevue(request);
        verify(ouvrageMapper).toResponse(ouvrage);
        verifyNoMoreInteractions(ouvrageService, ouvrageMapper);
    }

    @Test
    void findAll_shouldReturnMappedResponses() {
        Ouvrage o1 = mock(Ouvrage.class);
        Ouvrage o2 = mock(Ouvrage.class);

        OuvrageResponse r1 = mock(OuvrageResponse.class);
        OuvrageResponse r2 = mock(OuvrageResponse.class);

        when(ouvrageService.findAll()).thenReturn(List.of(o1, o2));
        when(ouvrageMapper.toResponse(o1)).thenReturn(r1);
        when(ouvrageMapper.toResponse(o2)).thenReturn(r2);

        List<OuvrageResponse> result = ouvrageController.findAll();

        assertEquals(List.of(r1, r2), result);
        verify(ouvrageService).findAll();
        verify(ouvrageMapper).toResponse(o1);
        verify(ouvrageMapper).toResponse(o2);
        verifyNoMoreInteractions(ouvrageService, ouvrageMapper);
    }

    @Test
    void rechercher_shouldReturnMappedResponses() {
        OuvrageSearchRequest request = mock(OuvrageSearchRequest.class);

        Ouvrage o1 = mock(Ouvrage.class);
        OuvrageResponse r1 = mock(OuvrageResponse.class);

        when(ouvrageService.rechercher(request)).thenReturn(List.of(o1));
        when(ouvrageMapper.toResponse(o1)).thenReturn(r1);

        List<OuvrageResponse> result = ouvrageController.rechercher(request);

        assertEquals(List.of(r1), result);
        verify(ouvrageService).rechercher(request);
        verify(ouvrageMapper).toResponse(o1);
        verifyNoMoreInteractions(ouvrageService, ouvrageMapper);
    }

    @Test
    void findById_shouldReturnMappedResponse() {
        Long id = 1L;
        Ouvrage ouvrage = mock(Ouvrage.class);
        OuvrageResponse response = mock(OuvrageResponse.class);

        when(ouvrageService.findById(id)).thenReturn(ouvrage);
        when(ouvrageMapper.toResponse(ouvrage)).thenReturn(response);

        OuvrageResponse result = ouvrageController.findById(id);

        assertEquals(response, result);
        verify(ouvrageService).findById(id);
        verify(ouvrageMapper).toResponse(ouvrage);
        verifyNoMoreInteractions(ouvrageService, ouvrageMapper);
    }
}