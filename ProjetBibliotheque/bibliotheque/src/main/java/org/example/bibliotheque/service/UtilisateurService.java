package org.example.bibliotheque.service;

import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.utilisateur.CreateUtilisateurRequest;
import org.example.bibliotheque.entity.Compte;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.exception.NotFoundException;
import org.example.bibliotheque.repository.CompteRepository;
import org.example.bibliotheque.repository.UtilisateurRepository;
import org.example.bibliotheque.service.factory.UtilisateurFactory;
import org.example.bibliotheque.service.factory.UtilisateurFactoryProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final CompteRepository compteRepository;
    private final UtilisateurFactoryProvider factoryProvider;

    @Transactional
    public Utilisateur create(CreateUtilisateurRequest request) {
        UtilisateurFactory factory = factoryProvider.getFactory(request.getTypeUtilisateur());
        Utilisateur utilisateur = factory.create(request);

        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);

        Compte compte = new Compte();
        compte.setUtilisateur(savedUtilisateur);
        compte.setCautionDisponible(request.getCautionInitiale());
        compteRepository.save(compte);

        savedUtilisateur.setCompte(compte);
        return savedUtilisateur;
    }
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));
    }
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }
    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));
    }
    public List<Utilisateur> searchByNomOrPrenom(String valeur) {
        return utilisateurRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(valeur, valeur);
    }
}
