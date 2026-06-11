package org.example.bibliotheque.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Livre extends Ouvrage {

    private String auteur;
    private String isbn;
    private Integer anneePublication;
}