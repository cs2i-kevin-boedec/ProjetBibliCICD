package org.example.bibliotheque.dto.ouvrage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OuvrageSearchRequest {
    private String titre;
    private String auteur;
    private Integer anneePublication;
    private String theme;
}