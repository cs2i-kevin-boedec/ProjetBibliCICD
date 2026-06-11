package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.ouvrage.OuvrageResponse;
import org.example.bibliotheque.entity.Livre;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.entity.Revue;
import org.springframework.stereotype.Component;

@Component
public class OuvrageMapper {

    public OuvrageResponse toResponse(Ouvrage ouvrage) {
        OuvrageResponse.OuvrageResponseBuilder builder = OuvrageResponse.builder()
                .id(ouvrage.getId())
                .type(ouvrage.getClass().getSimpleName())
                .titre(ouvrage.getTitre())
                .theme(ouvrage.getTheme())
                .caution(ouvrage.getCaution())
                .nombreExemplairesDisponibles(
                        (int) ouvrage.getExemplaires().stream().filter(ex -> ex.isDisponible()).count()
                );

        if (ouvrage instanceof Livre livre) {
            builder.auteur(livre.getAuteur())
                    .isbn(livre.getIsbn())
                    .anneePublication(livre.getAnneePublication());
        }

        if (ouvrage instanceof Revue revue) {
            builder.numeroVolume(revue.getNumeroVolume())
                    .dateParution(revue.getDateParution() != null ? revue.getDateParution().toString() : null);
        }

        return builder.build();
    }
}