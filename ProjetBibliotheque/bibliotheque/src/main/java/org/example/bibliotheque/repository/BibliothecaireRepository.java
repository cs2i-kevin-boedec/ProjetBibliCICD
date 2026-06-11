package org.example.bibliotheque.repository;

import org.example.bibliotheque.entity.Bibliothecaire;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BibliothecaireRepository extends JpaRepository<Bibliothecaire, Long> {
    Optional<Bibliothecaire> findByLogin(String login);
}