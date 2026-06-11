package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.reservation.ReservationResponse;
import org.example.bibliotheque.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponse toResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .utilisateurNomComplet(
                        reservation.getUtilisateur().getPrenom() + " " + reservation.getUtilisateur().getNom()
                )
                .ouvrageTitre(reservation.getOuvrage().getTitre())
                .dateReservation(
                        reservation.getDateReservation() != null ? reservation.getDateReservation().toString() : null
                )
                .statut(reservation.getStatut() != null ? reservation.getStatut().name() : null)
                .build();
    }
}