package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.reservation.ReservationResponse;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.entity.Reservation;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.enums.StatutReservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationMapperTest {

    private final ReservationMapper mapper = new ReservationMapper();

    @Test
    void toResponse_shouldMapAllFieldsCorrectly() {
        Utilisateur utilisateur = mock(Utilisateur.class);
        when(utilisateur.getPrenom()).thenReturn("Alice");
        when(utilisateur.getNom()).thenReturn("Martin");

        Ouvrage ouvrage = mock(Ouvrage.class);
        when(ouvrage.getTitre()).thenReturn("Design Patterns");

        Reservation reservation = mock(Reservation.class);
        when(reservation.getId()).thenReturn(1L);
        when(reservation.getUtilisateur()).thenReturn(utilisateur);
        when(reservation.getOuvrage()).thenReturn(ouvrage);
        when(reservation.getDateReservation()).thenReturn(LocalDate.of(2024, 5, 1));
        when(reservation.getStatut()).thenReturn(StatutReservation.ACTIVE);

        ReservationResponse response = mapper.toResponse(reservation);

        assertEquals(1L, response.getId());
        assertEquals("Alice Martin", response.getUtilisateurNomComplet());
        assertEquals("Design Patterns", response.getOuvrageTitre());
        assertEquals("2024-05-01", response.getDateReservation());
        assertEquals("ACTIVE", response.getStatut());
    }

    @Test
    void toResponse_shouldHandleNullDateAndStatut() {
        Utilisateur utilisateur = mock(Utilisateur.class);
        when(utilisateur.getPrenom()).thenReturn("Alice");
        when(utilisateur.getNom()).thenReturn("Martin");

        Ouvrage ouvrage = mock(Ouvrage.class);
        when(ouvrage.getTitre()).thenReturn("Design Patterns");

        Reservation reservation = mock(Reservation.class);
        when(reservation.getUtilisateur()).thenReturn(utilisateur);
        when(reservation.getOuvrage()).thenReturn(ouvrage);
        when(reservation.getDateReservation()).thenReturn(null);
        when(reservation.getStatut()).thenReturn(null);

        ReservationResponse response = mapper.toResponse(reservation);

        assertNull(response.getDateReservation());
        assertNull(response.getStatut());
    }
}