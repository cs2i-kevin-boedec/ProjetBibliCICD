package org.example.bibliotheque.service;

import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.repository.EmpruntRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetardService {

    private final EmpruntRepository empruntRepository;
    private final NotificationService notificationService;

    public boolean hasRetard(Long utilisateurId) {
        return empruntRepository.findByUtilisateurId(utilisateurId).stream()
                .anyMatch(emprunt ->
                        emprunt.getDateRetourEffective() == null &&
                                LocalDate.now().isAfter(emprunt.getDateRetourPrevue())
                );
    }

    public List<Emprunt> listRetards() {
        return empruntRepository.findByDateRetourPrevueBeforeAndDateRetourEffectiveIsNull(LocalDate.now());
    }

    public void envoyerRelances() {
        listRetards().forEach(notificationService::envoyerMailRetard);
    }
}