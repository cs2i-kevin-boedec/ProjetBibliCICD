package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.dto.ouvrage.RevueCreateRequest;
import org.example.bibliotheque.entity.Ouvrage;
import org.example.bibliotheque.entity.Revue;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RevueFactoryTest {

    private final RevueFactory factory = new RevueFactory();

    @Test
    void shouldCreateRevue() {
        RevueCreateRequest request = new RevueCreateRequest();
        request.setTitre("Science & Vie");
        request.setNumeroVolume(42);
        request.setDateParution(LocalDate.of(2025, 6, 15));
        request.setTheme("science");
        request.setCaution(10.0);

        Ouvrage result = factory.create(request);

        assertInstanceOf(Revue.class, result);
        Revue revue = (Revue) result;

        assertEquals("Science & Vie", revue.getTitre());
        assertEquals(42, revue.getNumeroVolume());
        assertEquals(LocalDate.of(2025, 6, 15), revue.getDateParution());
        assertEquals("science", revue.getTheme());
        assertEquals(10.0, revue.getCaution());
    }
}