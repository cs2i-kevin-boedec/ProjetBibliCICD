package org.example.bibliotheque.repository;

import org.example.bibliotheque.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}