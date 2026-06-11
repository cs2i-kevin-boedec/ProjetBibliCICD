package org.example.bibliotheque.dto.emprunt;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmpruntResponse {
    private Long id;
    private String utilisateurNomComplet;
    private String ouvrageTitre;
    private String codeBarreExemplaire;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private String statut;
}