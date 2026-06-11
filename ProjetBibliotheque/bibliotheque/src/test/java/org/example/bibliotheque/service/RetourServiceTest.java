package org.example.bibliotheque.service;

import org.example.bibliotheque.dto.retour.RetourRequest;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.entity.Exemplaire;
import org.example.bibliotheque.enums.EtatExemplaire;
import org.example.bibliotheque.enums.StatutEmprunt;
import org.example.bibliotheque.observer.RetardNotificationObserver;
import org.example.bibliotheque.repository.EmpruntRepository;
import org.example.bibliotheque.repository.ExemplaireRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetourServiceTest {

    @Mock
    private EmpruntRepository empruntRepository;

    @Mock
    private ExemplaireRepository exemplaireRepository;

    @Mock
    private RetardNotificationObserver retardNotificationObserver;

    @InjectMocks
    private RetourService retourService;

    @Test
    void shouldRegisterReturnAndSetExemplarAvailable() {
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setDisponible(false);

        Emprunt emprunt = new Emprunt();
        emprunt.setId(1L);
        emprunt.setExemplaire(exemplaire);
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(3));

        RetourRequest request = new RetourRequest();
        request.setEmpruntId(1L);
        request.setEtatExemplaire(EtatExemplaire.BON);

        when(empruntRepository.findById(1L)).thenReturn(Optional.of(emprunt));
        when(empruntRepository.save(any(Emprunt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Emprunt result = retourService.retourner(request);

        assertNotNull(result.getDateRetourEffective());
        assertEquals(StatutEmprunt.RETOURNE, result.getStatut());
        assertTrue(result.getExemplaire().isDisponible());
        assertEquals(EtatExemplaire.BON, result.getExemplaire().getEtat());
    }

    @Test
    void shouldSetLateStatusWhenReturnedAfterDueDate() {
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setDisponible(false);

        Emprunt emprunt = new Emprunt();
        emprunt.setId(1L);
        emprunt.setExemplaire(exemplaire);
        emprunt.setDateRetourPrevue(LocalDate.now().minusDays(2));

        RetourRequest request = new RetourRequest();
        request.setEmpruntId(1L);
        request.setEtatExemplaire(EtatExemplaire.ABIME);

        when(empruntRepository.findById(1L)).thenReturn(Optional.of(emprunt));
        when(empruntRepository.save(any(Emprunt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Emprunt result = retourService.retourner(request);

        assertEquals(StatutEmprunt.EN_RETARD, result.getStatut());
        assertTrue(result.getExemplaire().isDisponible());
        assertEquals(EtatExemplaire.ABIME, result.getExemplaire().getEtat());
    }
}