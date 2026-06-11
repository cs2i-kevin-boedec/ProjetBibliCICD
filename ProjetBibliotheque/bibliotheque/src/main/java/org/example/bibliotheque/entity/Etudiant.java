package org.example.bibliotheque.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Etudiant extends Utilisateur {
    private Integer anneeUniversitaire;
}