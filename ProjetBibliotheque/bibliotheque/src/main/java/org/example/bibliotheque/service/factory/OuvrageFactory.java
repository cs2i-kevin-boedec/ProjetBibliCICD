package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.entity.Ouvrage;

public interface OuvrageFactory<T> {
    Ouvrage create(T request);
}