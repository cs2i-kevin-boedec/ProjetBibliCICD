package org.example.bibliotheque.repository;

import org.example.bibliotheque.entity.EmplacementStockage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmplacementStockageRepository extends JpaRepository<EmplacementStockage, Long> {
    Optional<EmplacementStockage> findByTraveeAndEtagereAndNiveauAndCategorie(
            Integer travee,
            Integer etagere,
            String niveau,
            String categorie
    );
}