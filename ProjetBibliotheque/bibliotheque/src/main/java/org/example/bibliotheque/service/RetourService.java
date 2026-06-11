package org.example.bibliotheque.service;

import org.example.bibliotheque.dto.retour.RetourRequest;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.enums.EtatExemplaire;
import org.example.bibliotheque.enums.StatutEmprunt;
import org.example.bibliotheque.exception.NotFoundException;
import org.example.bibliotheque.observer.RetardNotificationObserver;
import org.example.bibliotheque.repository.EmpruntRepository;
import org.example.bibliotheque.repository.ExemplaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RetourService {
    private final EmpruntRepository empruntRepository;
    private final ExemplaireRepository exemplaireRepository;
    private final RetardNotificationObserver retardNotificationObserver;

    @Transactional
    public Emprunt retourner(RetourRequest request) {
        Emprunt emprunt = empruntRepository.findById(request.getEmpruntId())
                .orElseThrow(() -> new NotFoundException("Emprunt introuvable"));

        emprunt.attacher(retardNotificationObserver);
        emprunt.setDateRetourEffective(LocalDate.now());
        emprunt.setStatut(LocalDate.now().isAfter(emprunt.getDateRetourPrevue())
                ? StatutEmprunt.EN_RETARD
                : StatutEmprunt.RETOURNE);

        emprunt.getExemplaire().setDisponible(true);
        emprunt.getExemplaire().setEtat(request.getEtatExemplaire());
        exemplaireRepository.save(emprunt.getExemplaire());

        emprunt.notifier();
        return empruntRepository.save(emprunt);
    }
}