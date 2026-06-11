package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.ouvrage.RevueCreateRequest;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.entity.Revue;
import org.springframework.stereotype.Component;

@Component
public class RevueFactory implements OuvrageFactory<RevueCreateRequest> {

    @Override
    public Ouvrage create(RevueCreateRequest request) {
        Revue revue = new Revue();
        revue.setTitre(request.getTitre());
        revue.setNumeroVolume(request.getNumeroVolume());
        revue.setDateParution(request.getDateParution());
        revue.setTheme(request.getTheme());
        revue.setCaution(request.getCaution());
        return revue;
    }
}