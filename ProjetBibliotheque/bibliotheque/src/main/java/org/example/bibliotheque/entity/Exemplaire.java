package org.example.bibliotheque.entity;

import org.example.bibliotheque.enums.EtatExemplaire;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeBarre;

    @Enumerated(EnumType.STRING)
    private EtatExemplaire etat = EtatExemplaire.BON;

    private boolean disponible = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ouvrage_id")
    private Ouvrage ouvrage;
}