package org.example.bibliotheque.service;

import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.entity.Particulier;
import org.example.bibliotheque.repository.EmpruntRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetardServiceTest {

    @Mock
    private EmpruntRepository empruntRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private RetardService retardService;

    @Test
    void shouldReturnLateLoans() {
        Emprunt e1 = new Emprunt();
        e1.setDateRetourPrevue(LocalDate.now().minusDays(2));
        e1.setDateRetourEffective(null);

        when(empruntRepository.findByDateRetourPrevueBeforeAndDateRetourEffectiveIsNull(any(LocalDate.class)))
                .thenReturn(List.of(e1));

        List<Emprunt> result = retardService.listRetards();

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnEmptyWhenNoLateLoans() {
        when(empruntRepository.findByDateRetourPrevueBeforeAndDateRetourEffectiveIsNull(any(LocalDate.class)))
                .thenReturn(List.of());

        List<Emprunt> result = retardService.listRetards();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldDetectUserHasLateLoan() {
        Particulier utilisateur = new Particulier();
        utilisateur.setId(1L);

        Emprunt e1 = new Emprunt();
        e1.setUtilisateur(utilisateur);
        e1.setDateRetourPrevue(LocalDate.now().minusDays(1));
        e1.setDateRetourEffective(null);

        when(empruntRepository.findByUtilisateurId(1L))
                .thenReturn(List.of(e1));

        boolean result = retardService.hasRetard(1L);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenUserHasNoLateLoan() {
        Particulier utilisateur = new Particulier();
        utilisateur.setId(1L);

        Emprunt e1 = new Emprunt();
        e1.setUtilisateur(utilisateur);
        e1.setDateRetourPrevue(LocalDate.now().plusDays(3));
        e1.setDateRetourEffective(null);

        when(empruntRepository.findByUtilisateurId(1L))
                .thenReturn(List.of(e1));

        boolean result = retardService.hasRetard(1L);

        assertFalse(result);
    }

    @Test
    void shouldSendNotificationsForLateLoans() {
        Emprunt e1 = new Emprunt();
        e1.setDateRetourPrevue(LocalDate.now().minusDays(3));
        e1.setDateRetourEffective(null);

        when(empruntRepository.findByDateRetourPrevueBeforeAndDateRetourEffectiveIsNull(any(LocalDate.class)))
                .thenReturn(List.of(e1));

        retardService.envoyerRelances();

        verify(notificationService, times(1)).envoyerMailRetard(e1);
    }
}