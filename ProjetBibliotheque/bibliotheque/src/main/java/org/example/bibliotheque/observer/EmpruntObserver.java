package org.example.bibliotheque.observer;

import org.example.bibliotheque.entity.Emprunt;

public interface EmpruntObserver {
    void update(Emprunt emprunt);
}