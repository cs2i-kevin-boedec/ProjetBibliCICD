package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.emprunt.EmpruntResponse;
import org.example.bibliotheque.entity.Emprunt;
import org.springframework.stereotype.Component;

@Component
public class EmpruntMapper {

    public EmpruntResponse toResponse(Emprunt emprunt) {
        return EmpruntResponse.builder()
                .id(emprunt.getId())
                .utilisateurNomComplet(
                        emprunt.getUtilisateur().getPrenom() + " " + emprunt.getUtilisateur().getNom()
                )
                .ouvrageTitre(emprunt.getExemplaire().getOuvrage().getTitre())
                .codeBarreExemplaire(emprunt.getExemplaire().getCodeBarre())
                .dateEmprunt(emprunt.getDateEmprunt())
                .dateRetourPrevue(emprunt.getDateRetourPrevue())
                .dateRetourEffective(emprunt.getDateRetourEffective())
                .statut(emprunt.getStatut() != null ? emprunt.getStatut().name() : null)
                .build();
    }
}