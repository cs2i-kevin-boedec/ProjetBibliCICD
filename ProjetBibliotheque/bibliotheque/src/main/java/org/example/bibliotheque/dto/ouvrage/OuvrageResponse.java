package org.example.bibliotheque.dto.ouvrage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OuvrageResponse {
    private Long id;
    private String type;
    private String titre;
    private String auteur;
    private String isbn;
    private Integer anneePublication;
    private Integer numeroVolume;
    private String dateParution;
    private String theme;
    private Double caution;
    private Integer nombreExemplairesDisponibles;
}