package org.example.bibliotheque.repository;

import org.example.bibliotheque.entity.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
    Optional<Exemplaire> findFirstByOuvrageIdAndDisponibleTrue(Long ouvrageId);
}