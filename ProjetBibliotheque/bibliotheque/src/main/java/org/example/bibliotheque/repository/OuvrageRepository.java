package org.example.bibliotheque.repository;

import org.example.bibliotheque.entity.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OuvrageRepository extends JpaRepository<Ouvrage, Long>, JpaSpecificationExecutor<Ouvrage> {
}