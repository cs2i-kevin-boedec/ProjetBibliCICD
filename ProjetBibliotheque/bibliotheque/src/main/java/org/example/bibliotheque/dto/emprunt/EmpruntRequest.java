package org.example.bibliotheque.dto.emprunt;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpruntRequest {

    @NotNull
    private Long utilisateurId;

    @NotNull
    private Long ouvrageId;
}