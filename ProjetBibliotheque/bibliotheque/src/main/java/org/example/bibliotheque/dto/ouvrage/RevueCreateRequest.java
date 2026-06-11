package org.example.bibliotheque.dto.ouvrage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RevueCreateRequest {

    @NotBlank
    private String titre;

    @NotNull
    private Integer numeroVolume;

    @NotNull
    private LocalDate dateParution;

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
