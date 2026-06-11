package org.example.bibliotheque.service;

import org.example.bibliotheque.dto.emprunt.EmpruntRequest;
import org.example.bibliotheque.entity.Compte;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.entity.Exemplaire;
import org.example.bibliotheque.entity.Livre;
import org.example.bibliotheque.entity.Particulier;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.enums.StatutEmprunt;
import org.example.bibliotheque.exception.BusinessException;
import org.example.bibliotheque.repository.EmpruntRepository;
import org.example.bibliotheque.repository.ExemplaireRepository;
import org.example.bibliotheque.repository.OuvrageRepository;
import org.example.bibliotheque.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpruntServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private OuvrageRepository ouvrageRepository;

    @Mock
    private ExemplaireRepository exemplaireRepository;

    @Mock
    private EmpruntRepository empruntRepository;

    @Mock
    private RetardService retardService;

    @InjectMocks
    private EmpruntService empruntService;

    private Utilisateur utilisateur;
    private Livre livre;
    private Exemplaire exemplaire;
    private Compte compte;

    @BeforeEach
    void setUp() {
        utilisateur = new Particulier();
        utilisateur.setId(1L);
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Alice");

        compte = new Compte();
        compte.setId(1L);
        compte.setCautionDisponible(100.0);
        compte.setUtilisateur(utilisateur);
        utilisateur.setCompte(compte);

        livre = new Livre();
        livre.setId(10L);
        livre.setTitre("Java propre");
        livre.setCaution(20.0);

        exemplaire = new Exemplaire();
        exemplaire.setId(100L);
        exemplaire.setCodeBarre("CB-001");
        exemplaire.setDisponible(true);
        exemplaire.setOuvrage(livre);
    }

    @Test
    void shouldCreateLoanWhenEverythingIsValid() {
        EmpruntRequest request = new EmpruntRequest();
        request.setUtilisateurId(1L);
        request.setOuvrageId(10L);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(ouvrageRepository.findById(10L)).thenReturn(Optional.of(livre));
        when(retardService.hasRetard(1L)).thenReturn(false);
        when(empruntRepository.existsByUtilisateurIdAndExemplaireOuvrageIdAndStatut(1L, 10L, StatutEmprunt.EN_COURS))
                .thenReturn(false);
        when(exemplaireRepository.findFirstByOuvrageIdAndDisponibleTrue(10L)).thenReturn(Optional.of(exemplaire));
        when(empruntRepository.save(any(Emprunt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Emprunt result = empruntService.emprunter(request);

        assertNotNull(result);
        assertEquals(utilisateur, result.getUtilisateur());
        assertEquals(exemplaire, result.getExemplaire());
        assertEquals(StatutEmprunt.EN_COURS, result.getStatut());
        assertFalse(exemplaire.isDisponible());
        assertEquals(80.0, utilisateur.getCompte().getCautionDisponible());
        assertNotNull(result.getDateEmprunt());
        assertNotNull(result.getDateRetourPrevue());
    }

    @Test
    void shouldThrowWhenUserHasLateLoan() {
        EmpruntRequest request = new EmpruntRequest();
        request.setUtilisateurId(1L);
        request.setOuvrageId(10L);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(ouvrageRepository.findById(10L)).thenReturn(Optional.of(livre));
        when(retardService.hasRetard(1L)).thenReturn(true);

        assertThrows(BusinessException.class, () -> empruntService.emprunter(request));
    }

    @Test
    void shouldThrowWhenDepositIsInsufficient() {
        compte.setCautionDisponible(10.0);

        EmpruntRequest request = new EmpruntRequest();
        request.setUtilisateurId(1L);
        request.setOuvrageId(10L);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(ouvrageRepository.findById(10L)).thenReturn(Optional.of(livre));
        when(retardService.hasRetard(1L)).thenReturn(false);
        when(empruntRepository.existsByUtilisateurIdAndExemplaireOuvrageIdAndStatut(1L, 10L, StatutEmprunt.EN_COURS))
                .thenReturn(false);

        assertThrows(BusinessException.class, () -> empruntService.emprunter(request));
    }

    @Test
    void shouldThrowWhenSameResourceAlreadyBorrowed() {
        EmpruntRequest request = new EmpruntRequest();
        request.setUtilisateurId(1L);
        request.setOuvrageId(10L);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(ouvrageRepository.findById(10L)).thenReturn(Optional.of(livre));
        when(retardService.hasRetard(1L)).thenReturn(false);
        when(empruntRepository.existsByUtilisateurIdAndExemplaireOuvrageIdAndStatut(1L, 10L, StatutEmprunt.EN_COURS))
                .thenReturn(true);

        assertThrows(BusinessException.class, () -> empruntService.emprunter(request));
    }
}