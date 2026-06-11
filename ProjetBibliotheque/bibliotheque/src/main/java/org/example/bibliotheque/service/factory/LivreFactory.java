package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.ouvrage.LivreCreateRequest;
import org.example.bibliotheque.entity.Livre;
import org.example.bibliotheque.entity.Ouvrage;
import org.springframework.stereotype.Component;

@Component
public class LivreFactory implements OuvrageFactory<LivreCreateRequest> {

    @Override
    public Ouvrage create(LivreCreateRequest request) {
        Livre livre = new Livre();
        livre.setTitre(request.getTitre());
        livre.setAuteur(request.getAuteur());
        livre.setIsbn(request.getIsbn());
        livre.setAnneePublication(request.getAnneePublication());
        livre.setTheme(request.getTheme());
        livre.setCaution(request.getCaution());
        return livre;
    }
}