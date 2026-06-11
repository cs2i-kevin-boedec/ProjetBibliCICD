package org.example.bibliotheque.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {

    @NotNull
    private Long utilisateurId;

    @NotNull
    private Long ouvrageId;
}