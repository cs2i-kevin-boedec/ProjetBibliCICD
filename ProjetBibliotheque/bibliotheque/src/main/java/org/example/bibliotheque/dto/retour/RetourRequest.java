package org.example.bibliotheque.dto.retour;

import org.example.bibliotheque.enums.EtatExemplaire;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetourRequest {

    @NotNull
    private Long empruntId;

    @NotNull
    private EtatExemplaire etatExemplaire;
}