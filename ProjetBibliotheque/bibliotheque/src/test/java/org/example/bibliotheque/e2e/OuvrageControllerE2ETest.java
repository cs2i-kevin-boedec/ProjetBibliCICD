package org.example.bibliotheque.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OuvrageControllerE2ETest extends BaseE2ETest {

    @Test
    void shouldCreateLivre() throws Exception {
        mockMvc.perform(post("/api/ouvrages/livres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "titre": "Clean Code",
                      "auteur": "Robert C. Martin",
                      "isbn": "9780132350884",
                      "anneePublication": 2008,
                      "theme": "Programmation",
                      "caution": 25.0,
                      "nombreExemplaires": 3,
                      "travee": 1,
                      "etagere": 2,
                      "niveau": "1",
                      "categorie": "Informatique"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titre").value("Clean Code"));
    }

    @Test
    void shouldFindAllOuvrages() throws Exception {
        mockMvc.perform(post("/api/ouvrages/livres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "titre": "DDD",
                      "auteur": "Eric Evans",
                      "isbn": "9780321125217",
                      "anneePublication": 2003,
                      "theme": "Architecture logicielle",
                      "caution": 20.0,
                      "nombreExemplaires": 2,
                      "travee": 1,
                      "etagere": 3,
                      "niveau": "1",
                      "categorie": "Informatique"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(get("/api/ouvrages"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldSearchOuvrage() throws Exception {
        mockMvc.perform(post("/api/ouvrages/livres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "titre": "Spring in Action",
                      "auteur": "Craig Walls",
                      "isbn": "9781617294945",
                      "anneePublication": 2018,
                      "theme": "Spring",
                      "caution": 30.0,
                      "nombreExemplaires": 4,
                      "travee": 2,
                      "etagere": 1,
                      "niveau": "1",
                      "categorie": "Informatique"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(get("/api/ouvrages/search")
                        .param("titre", "Spring"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
}