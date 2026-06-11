package org.example.bibliotheque.observer;

import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetardNotificationObserver implements EmpruntObserver {
    private final NotificationService notificationService;

    @Override
    public void update(Emprunt emprunt) {
        if (emprunt.estEnRetard()) {
            notificationService.envoyerMailRetard(emprunt);
        }
    }
}