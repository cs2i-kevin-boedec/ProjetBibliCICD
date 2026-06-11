package org.example.bibliotheque.service;

import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Compte;
import org.example.bibliotheque.entity.Etudiant;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.enums.TypeUtilisateur;
import org.example.bibliotheque.repository.CompteRepository;
import org.example.bibliotheque.repository.UtilisateurRepository;
import org.example.bibliotheque.service.factory.UtilisateurFactory;
import org.example.bibliotheque.service.factory.UtilisateurFactoryProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private UtilisateurFactoryProvider factoryProvider;

    @Mock
    private UtilisateurFactory utilisateurFactory;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @Test
    void shouldCreateUserAndAssociatedAccount() {
        CreateUtilisateurRequest request = new CreateUtilisateurRequest();
        request.setTypeUtilisateur(TypeUtilisateur.ETUDIANT);
        request.setNom("Dupont");
        request.setPrenom("Alice");
        request.setAdresse("Lorient");
        request.setEmail("alice@test.fr");
        request.setLogin("alice");
        request.setMotDePasse("alice123");
        request.setCautionInitiale(100.0);

        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Dupont");

        when(factoryProvider.getFactory(TypeUtilisateur.ETUDIANT)).thenReturn(utilisateurFactory);
        when(utilisateurFactory.create(request)).thenReturn(etudiant);
        when(utilisateurRepository.save(any(Utilisateur.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(compteRepository.save(any(Compte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Utilisateur result = utilisateurService.create(request);

        assertNotNull(result);
        assertNotNull(result.getCompte());
        assertEquals(100.0, result.getCompte().getCautionDisponible());
        assertEquals(result, result.getCompte().getUtilisateur());
    }
}