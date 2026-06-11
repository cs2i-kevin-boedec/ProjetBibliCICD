package org.example.bibliotheque.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Enseignant extends Utilisateur {
    private String departement;
}