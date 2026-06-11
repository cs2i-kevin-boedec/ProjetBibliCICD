package org.example.bibliotheque.dto.reservation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {
    private Long id;
    private String utilisateurNomComplet;
    private String ouvrageTitre;
    private String dateReservation;
    private String statut;
}