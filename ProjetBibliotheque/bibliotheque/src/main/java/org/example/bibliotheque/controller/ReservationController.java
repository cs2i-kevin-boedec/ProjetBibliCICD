package org.example.bibliotheque.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bibliotheque.dto.reservation.ReservationRequest;
import org.example.bibliotheque.dto.reservation.ReservationResponse;
import org.example.bibliotheque.entity.Reservation;
import org.example.bibliotheque.mapper.ReservationMapper;
import org.example.bibliotheque.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse reserver(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = reservationService.reserver(request);
        return reservationMapper.toResponse(reservation);
    }
}