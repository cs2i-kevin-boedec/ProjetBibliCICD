package org.example.bibliotheque.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Revue extends Ouvrage {
    private Integer numeroVolume;
    private LocalDate dateParution;
}