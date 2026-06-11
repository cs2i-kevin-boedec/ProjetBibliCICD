package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.ouvrage.LivreCreateRequest;
import org.example.bibliotheque.entity.Livre;
import org.example.bibliotheque.entity.Ouvrage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivreFactoryTest {

    private final LivreFactory factory = new LivreFactory();

    @Test
    void shouldCreateLivre() {
        LivreCreateRequest request = new LivreCreateRequest();
        request.setTitre("Java propre");
        request.setAuteur("Robert Martin");
        request.setIsbn("9780132350884");
        request.setAnneePublication(2008);
        request.setTheme("informatique");
        request.setCaution(20.0);

        Ouvrage result = factory.create(request);

        assertInstanceOf(Livre.class, result);
        Livre livre = (Livre) result;

        assertEquals("Java propre", livre.getTitre());
        assertEquals("Robert Martin", livre.getAuteur());
        assertEquals("9780132350884", livre.getIsbn());
        assertEquals(2008, livre.getAnneePublication());
        assertEquals("informatique", livre.getTheme());
        assertEquals(20.0, livre.getCaution());
    }
}