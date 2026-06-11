package org.example.bibliotheque.observer;

import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetardNotificationObserverTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private RetardNotificationObserver observer;

    @Test
    void shouldSendMailWhenEmpruntIsLate() {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateRetourPrevue(LocalDate.now().minusDays(3));
        emprunt.setDateRetourEffective(null);

        observer.update(emprunt);

        verify(notificationService, times(1))
                .envoyerMailRetard(emprunt);
    }

    @Test
    void shouldNotSendMailWhenEmpruntIsNotLate() {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(3));
        emprunt.setDateRetourEffective(null);

        observer.update(emprunt);

        verify(notificationService, never())
                .envoyerMailRetard(any());
    }
}