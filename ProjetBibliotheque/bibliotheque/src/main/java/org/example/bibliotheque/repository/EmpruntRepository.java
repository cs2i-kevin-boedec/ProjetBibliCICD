package org.example.bibliotheque.repository;

import org.example.bibliotheque.entity.Emprunt;
import org.example.bibliotheque.enums.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    List<Emprunt> findByUtilisateurId(Long utilisateurId);

    List<Emprunt> findByUtilisateurIdAndStatut(Long utilisateurId, StatutEmprunt statut);

    boolean existsByUtilisateurIdAndExemplaireOuvrageIdAndStatut(
            Long utilisateurId,
            Long ouvrageId,
            StatutEmprunt statut
    );

    List<Emprunt> findByDateRetourPrevueBeforeAndDateRetourEffectiveIsNull(LocalDate date);
}