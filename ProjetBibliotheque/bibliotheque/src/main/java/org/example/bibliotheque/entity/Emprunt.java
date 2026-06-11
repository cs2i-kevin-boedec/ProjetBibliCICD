package org.example.bibliotheque.entity;

import org.example.bibliotheque.enums.StatutEmprunt;
import org.example.bibliotheque.observer.EmpruntObserver;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    @Enumerated(EnumType.STRING)
    private StatutEmprunt statut;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exemplaire_id")
    private Exemplaire exemplaire;

    @Transient
    private final List<EmpruntObserver> observers = new ArrayList<>();

    public boolean estEnRetard() {
        return dateRetourEffective == null && LocalDate.now().isAfter(dateRetourPrevue);
    }

    public void attacher(EmpruntObserver observer) {
        observers.add(observer);
    }

    public void detacher(EmpruntObserver observer) {
        observers.remove(observer);
    }

    public void notifier() {
        observers.forEach(observer -> observer.update(this));
    }
}