package org.example.bibliotheque.controller;

import org.example.bibliotheque.dto.reservation.ReservationRequest;
import org.example.bibliotheque.dto.reservation.ReservationResponse;
import org.example.bibliotheque.entity.Reservation;
import org.example.bibliotheque.mapper.ReservationMapper;
import org.example.bibliotheque.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    void reserver_shouldCallServiceAndMapper_andReturnResponse() {
        // Arrange
        ReservationRequest request = mock(ReservationRequest.class);
        Reservation reservation = mock(Reservation.class);
        ReservationResponse response = mock(ReservationResponse.class);

        when(reservationService.reserver(request)).thenReturn(reservation);
        when(reservationMapper.toResponse(reservation)).thenReturn(response);

        // Act
        ReservationResponse result = reservationController.reserver(request);

        // Assert
        assertEquals(response, result);
        verify(reservationService, times(1)).reserver(request);
        verify(reservationMapper, times(1)).toResponse(reservation);
        verifyNoMoreInteractions(reservationService, reservationMapper);
    }
}