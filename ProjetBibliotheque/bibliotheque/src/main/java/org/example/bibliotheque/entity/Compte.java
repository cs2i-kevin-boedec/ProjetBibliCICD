package org.example.bibliotheque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cautionDisponible;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false, unique = true)
    private Utilisateur utilisateur;

    public boolean peutEmprunter(Double cautionOuvrage) {
        return cautionDisponible != null && cautionDisponible > cautionOuvrage;
    }

    public void debiter(Double montant) {
        this.cautionDisponible -= montant;
    }

    public void crediter(Double montant) {
        this.cautionDisponible += montant;
    }
}