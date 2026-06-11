package org.example.bibliotheque.service;

import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.reservation.ReservationRequest;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.entity.Reservation;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.enums.StatutReservation;
import org.example.bibliotheque.exception.BusinessException;
import org.example.bibliotheque.exception.NotFoundException;
import org.example.bibliotheque.repository.OuvrageRepository;
import org.example.bibliotheque.repository.ReservationRepository;
import org.example.bibliotheque.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UtilisateurRepository utilisateurRepository;
    private final OuvrageRepository ouvrageRepository;
    private final ReservationRepository reservationRepository;
    private final RetardService retardService;

    @Transactional
    public Reservation reserver(ReservationRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

        Ouvrage ouvrage = ouvrageRepository.findById(request.getOuvrageId())
                .orElseThrow(() -> new NotFoundException("Ouvrage introuvable"));

        if (retardService.hasRetard(utilisateur.getId())) {
            throw new BusinessException("Utilisateur en retard, réservation impossible");
        }

        Reservation reservation = new Reservation();
        reservation.setUtilisateur(utilisateur);
        reservation.setOuvrage(ouvrage);
        reservation.setDateReservation(LocalDate.now());
        reservation.setStatut(StatutReservation.ACTIVE);

        return reservationRepository.save(reservation);
    }
}