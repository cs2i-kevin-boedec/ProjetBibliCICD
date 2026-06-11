package org.example.bibliotheque.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ouvrage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String theme;
    private Double caution;

    @ManyToOne(optional = false)
    @JoinColumn(name = "emplacement_id")
    private EmplacementStockage emplacementStockage;

    @OneToMany(mappedBy = "ouvrage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exemplaire> exemplaires = new ArrayList<>();

}