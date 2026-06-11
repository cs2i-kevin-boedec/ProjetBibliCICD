package org.example.bibliotheque.e2e;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmpruntControllerE2ETest extends BaseE2ETest {

    @Test
    void shouldCreateEmprunt() throws Exception {
        MvcResult utilisateurResult = mockMvc.perform(post("/api/utilisateurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "typeUtilisateur": "ETUDIANT",
                      "nom": "Durand",
                      "prenom": "Paul",
                      "adresse": "10 rue des Lilas",
                      "email": "pdurand@test.com",
                      "login": "pdurand",
                      "motDePasse": "Password123!",
                      "cautionInitiale": 100.0,
                      "anneeUniversitaire": 2,
                      "departement": "Informatique"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String utilisateurResponse = utilisateurResult.getResponse().getContentAsString();
        JsonNode utilisateurJson = objectMapper.readTree(utilisateurResponse);
        Long utilisateurId = utilisateurJson.get("id").asLong();

        MvcResult ouvrageResult = mockMvc.perform(post("/api/ouvrages/livres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "titre": "Effective Java",
                      "auteur": "Joshua Bloch",
                      "isbn": "9780134685991",
                      "anneePublication": 2018,
                      "theme": "Java",
                      "caution": 35.0,
                      "nombreExemplaires": 5,
                      "travee": 2,
                      "etagere": 4,
                      "niveau": "1",
                      "categorie": "Informatique"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String ouvrageResponse = ouvrageResult.getResponse().getContentAsString();
        JsonNode ouvrageJson = objectMapper.readTree(ouvrageResponse);
        Long ouvrageId = ouvrageJson.get("id").asLong();

        mockMvc.perform(post("/api/emprunts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "utilisateurId": %d,
                      "ouvrageId": %d
                    }
                    """.formatted(utilisateurId, ouvrageId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldListEmpruntsByUtilisateur() throws Exception {
        // même logique de préparation puis vérification GET
    }
}