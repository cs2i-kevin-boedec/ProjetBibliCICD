package org.example.bibliotheque.service;

import org.example.bibliotheque.entity.Emprunt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {
    public void envoyerMailRetard(Emprunt emprunt) {
        log.info("Mail de retard envoyé à {} pour l'emprunt {}", emprunt.getUtilisateur().getEmail(), emprunt.getId());
    }
}