package org.example.bibliotheque.repository;

import org.example.bibliotheque.entity.Compte;
import org.example.bibliotheque.entity.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface CompteRepository extends JpaRepository<Compte, Long> {
}