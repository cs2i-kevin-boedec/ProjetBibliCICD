package org.example.bibliotheque.dto.ouvrage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivreCreateRequest {

    @NotBlank
    private String titre;

    @NotBlank
    private String auteur;

    @NotBlank
    private String isbn;

    @NotNull
    private Integer anneePublication;

    private String theme;

    @NotNull
    private Double caution;

    @NotNull
    private Integer nombreExemplaires;

    @NotNull
    private Integer travee;

    @NotNull
    private Integer etagere;

    @NotBlank
    private String niveau;

    @NotBlank
    private String categorie;
}