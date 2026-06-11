package org.example.bibliotheque.mapper;

import org.example.bibliotheque.dto.ouvrage.OuvrageResponse;
import org.example.bibliotheque.entity.Exemplaire;
import org.example.bibliotheque.entity.Livre;
import org.example.bibliotheque.entity.Revue;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OuvrageMapperTest {

    private final OuvrageMapper mapper = new OuvrageMapper();

    @Test
    void toResponse_shouldMapLivreCorrectly() {
        // Arrange
        Livre livre = mock(Livre.class);

        Exemplaire ex1 = mock(Exemplaire.class);
        Exemplaire ex2 = mock(Exemplaire.class);

        when(ex1.isDisponible()).thenReturn(true);
        when(ex2.isDisponible()).thenReturn(false);

        when(livre.getId()).thenReturn(1L);
        when(livre.getTitre()).thenReturn("Clean Code");
        when(livre.getTheme()).thenReturn("Informatique");
        when(livre.getCaution()).thenReturn(10.0);
        when(livre.getExemplaires()).thenReturn(List.of(ex1, ex2));

        when(livre.getAuteur()).thenReturn("Robert C. Martin");
        when(livre.getIsbn()).thenReturn("123456789");
        when(livre.getAnneePublication()).thenReturn(2008);

        // Act
        OuvrageResponse response = mapper.toResponse(livre);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Livre", response.getType());
        assertEquals("Clean Code", response.getTitre());
        assertEquals("Informatique", response.getTheme());
        assertEquals(10.0, response.getCaution());
        assertEquals(1, response.getNombreExemplairesDisponibles());

        assertEquals("Robert C. Martin", response.getAuteur());
        assertEquals("123456789", response.getIsbn());
        assertEquals(2008, response.getAnneePublication());
    }

    @Test
    void toResponse_shouldMapRevueCorrectly() {
        // Arrange
        Revue revue = mock(Revue.class);

        Exemplaire ex = mock(Exemplaire.class);
        when(ex.isDisponible()).thenReturn(true);

        when(revue.getId()).thenReturn(2L);
        when(revue.getTitre()).thenReturn("Science Mag");
        when(revue.getTheme()).thenReturn("Science");
        when(revue.getCaution()).thenReturn(5.0);
        when(revue.getExemplaires()).thenReturn(List.of(ex));

        when(revue.getNumeroVolume()).thenReturn(42);
        when(revue.getDateParution()).thenReturn(LocalDate.of(2024, 1, 1));

        // Act
        OuvrageResponse response = mapper.toResponse(revue);

        // Assert
        assertEquals("Revue", response.getType());
        assertEquals(1, response.getNombreExemplairesDisponibles());
        assertEquals(42, response.getNumeroVolume());
        assertEquals("2024-01-01", response.getDateParution());
    }

    @Test
    void toResponse_shouldHandleNullDateParution() {
        // Arrange
        Revue revue = mock(Revue.class);

        when(revue.getExemplaires()).thenReturn(List.of());
        when(revue.getDateParution()).thenReturn(null);

        // Act
        OuvrageResponse response = mapper.toResponse(revue);

        // Assert
        assertNull(response.getDateParution());
    }
}