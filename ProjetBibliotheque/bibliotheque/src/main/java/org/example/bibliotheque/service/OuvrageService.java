package org.example.bibliotheque.service;

import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.ouvrage.LivreCreateRequest;
import org.example.bibliotheque.dto.ouvrage.OuvrageSearchRequest;
import org.example.bibliotheque.dto.ouvrage.RevueCreateRequest;
import org.example.bibliotheque.entity.EmplacementStockage;
import org.example.bibliotheque.entity.Exemplaire;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.exception.NotFoundException;
import org.example.bibliotheque.repository.EmplacementStockageRepository;
import org.example.bibliotheque.repository.ExemplaireRepository;
import org.example.bibliotheque.repository.OuvrageRepository;
import org.example.bibliotheque.service.factory.LivreFactory;
import org.example.bibliotheque.service.factory.RevueFactory;
import org.example.bibliotheque.dto.ouvrage.OuvrageSearchRequest;
import org.example.bibliotheque.specification.OuvrageSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OuvrageService {

    private final OuvrageRepository ouvrageRepository;
    private final ExemplaireRepository exemplaireRepository;
    private final EmplacementStockageRepository emplacementRepository;
    private final LivreFactory livreFactory;
    private final RevueFactory revueFactory;

    @Transactional
    public Ouvrage createLivre(LivreCreateRequest request) {
        Ouvrage ouvrage = livreFactory.create(request);
        EmplacementStockage emplacement = findOrCreateEmplacement(
                request.getTravee(),
                request.getEtagere(),
                request.getNiveau(),
                request.getCategorie()
        );
        ouvrage.setEmplacementStockage(emplacement);
        Ouvrage saved = ouvrageRepository.save(ouvrage);

        for (int i = 0; i < request.getNombreExemplaires(); i++) {
            Exemplaire exemplaire = new Exemplaire();
            exemplaire.setCodeBarre(UUID.randomUUID().toString());
            exemplaire.setDisponible(true);
            exemplaire.setOuvrage(saved);
            exemplaireRepository.save(exemplaire);
        }

        return saved;
    }

    @Transactional
    public Ouvrage createRevue(RevueCreateRequest request) {
        Ouvrage ouvrage = revueFactory.create(request);
        EmplacementStockage emplacement = findOrCreateEmplacement(
                request.getTravee(),
                request.getEtagere(),
                request.getNiveau(),
                request.getCategorie()
        );
        ouvrage.setEmplacementStockage(emplacement);
        Ouvrage saved = ouvrageRepository.save(ouvrage);

        for (int i = 0; i < request.getNombreExemplaires(); i++) {
            Exemplaire exemplaire = new Exemplaire();
            exemplaire.setCodeBarre(UUID.randomUUID().toString());
            exemplaire.setDisponible(true);
            exemplaire.setOuvrage(saved);
            exemplaireRepository.save(exemplaire);
        }

        return saved;
    }

    public List<Ouvrage> findAll() {
        return ouvrageRepository.findAll();
    }

    private EmplacementStockage findOrCreateEmplacement(
            Integer travee,
            Integer etagere,
            String niveau,
            String categorie
    ) {
        return emplacementRepository
                .findByTraveeAndEtagereAndNiveauAndCategorie(travee, etagere, niveau, categorie)
                .orElseGet(() -> {
                    EmplacementStockage e = new EmplacementStockage();
                    e.setTravee(travee);
                    e.setEtagere(etagere);
                    e.setNiveau(niveau);
                    e.setCategorie(categorie);
                    return emplacementRepository.save(e);
                });
    }
    public List<Ouvrage> rechercher(OuvrageSearchRequest request) {
        return ouvrageRepository.findAll(OuvrageSpecification.withCriteria(request));
    }
    public Ouvrage findById(Long id) {
        return ouvrageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ouvrage introuvable"));
    }
}
