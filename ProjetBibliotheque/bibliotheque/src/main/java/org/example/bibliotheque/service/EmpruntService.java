package org.example.bibliotheque.service;

import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.emprunt.EmpruntRequest;
import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.entity.Exemplaire;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.entity.Utilisateur;
import org.example.bibliotheque.enums.StatutEmprunt;
import org.example.bibliotheque.exception.BusinessException;
import org.example.bibliotheque.exception.NotFoundException;
import org.example.bibliotheque.repository.EmpruntRepository;
import org.example.bibliotheque.repository.ExemplaireRepository;
import org.example.bibliotheque.repository.OuvrageRepository;
import org.example.bibliotheque.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpruntService {

    private final UtilisateurRepository utilisateurRepository;
    private final OuvrageRepository ouvrageRepository;
    private final ExemplaireRepository exemplaireRepository;
    private final EmpruntRepository empruntRepository;
    private final RetardService retardService;

    @Transactional
    public Emprunt emprunter(EmpruntRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

        Ouvrage ouvrage = ouvrageRepository.findById(request.getOuvrageId())
                .orElseThrow(() -> new NotFoundException("Ouvrage introuvable"));

        if (retardService.hasRetard(utilisateur.getId())) {
            throw new BusinessException("Utilisateur en retard, emprunt impossible");
        }

        if (empruntRepository.existsByUtilisateurIdAndExemplaireOuvrageIdAndStatut(
                utilisateur.getId(),
                ouvrage.getId(),
                StatutEmprunt.EN_COURS
        )) {
            throw new BusinessException("Impossible d'emprunter deux exemplaires de la même ressource");
        }

        if (!utilisateur.getCompte().peutEmprunter(ouvrage.getCaution())) {
            throw new BusinessException("Caution insuffisante");
        }

        Exemplaire exemplaire = exemplaireRepository.findFirstByOuvrageIdAndDisponibleTrue(ouvrage.getId())
                .orElseThrow(() -> new BusinessException("Aucun exemplaire disponible"));

        utilisateur.getCompte().debiter(ouvrage.getCaution());
        exemplaire.setDisponible(false);

        Emprunt emprunt = new Emprunt();
        emprunt.setUtilisateur(utilisateur);
        emprunt.setExemplaire(exemplaire);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(15));
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        return empruntRepository.save(emprunt);
    }

    public List<Emprunt> listByUtilisateur(Long utilisateurId) {
        return empruntRepository.findByUtilisateurId(utilisateurId);
    }
    public List<Emprunt> findActifsByUtilisateur(Long utilisateurId) {
        return empruntRepository.findByUtilisateurId(utilisateurId).stream()
                .filter(emprunt -> emprunt.getStatut() == org.example.bibliotheque.enums.StatutEmprunt.EN_COURS
                        || emprunt.getStatut() == org.example.bibliotheque.enums.StatutEmprunt.EN_RETARD)
                .toList();
    }
}