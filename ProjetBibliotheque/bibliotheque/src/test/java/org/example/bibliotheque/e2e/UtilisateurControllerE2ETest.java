package org.example.bibliotheque.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UtilisateurControllerE2ETest extends BaseE2ETest {

    @Test
    void shouldCreateUtilisateur() throws Exception {
        String requestBody = """
            {
              "typeUtilisateur": "ETUDIANT",
              "nom": "Dupont",
              "prenom": "Jean",
              "adresse": "12 rue des Fleurs",
              "email": "jdupont@test.com",
              "login": "jdupont",
              "motDePasse": "Password123!",
              "cautionInitiale": 100.0,
              "anneeUniversitaire": 2,
              "departement": "Informatique"
            }
            """;

        mockMvc.perform(post("/api/utilisateurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.prenom").value("Jean"))
                .andExpect(jsonPath("$.login").value("jdupont"))
                .andExpect(jsonPath("$.typeUtilisateur").value("Etudiant"));
    }

    @Test
    void shouldFindUtilisateurByLogin() throws Exception {
        String requestBody = """
            {
              "typeUtilisateur": "ETUDIANT",
              "nom": "Martin",
              "prenom": "Claire",
              "adresse": "8 avenue Victor Hugo",
              "email": "cmartin@test.com",
              "login": "cmartin",
              "motDePasse": "Password123!",
              "cautionInitiale": 80.0,
              "anneeUniversitaire": 3,
              "departement": "Lettres"
            }
            """;

        mockMvc.perform(post("/api/utilisateurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/utilisateurs/by-login/cmartin"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.login").value("cmartin"))
                .andExpect(jsonPath("$.nom").value("Martin"))
                .andExpect(jsonPath("$.prenom").value("Claire"))
                .andExpect(jsonPath("$.typeUtilisateur").value("Etudiant"));
    }

    @Test
    void shouldSearchUtilisateurByNomOrPrenom() throws Exception {
        mockMvc.perform(post("/api/utilisateurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "typeUtilisateur": "ETUDIANT",
                      "nom": "Bernard",
                      "prenom": "Alice",
                      "adresse": "5 boulevard Saint-Michel",
                      "email": "abernard@test.com",
                      "login": "abernard",
                      "motDePasse": "Password123!",
                      "cautionInitiale": 120.0,
                      "anneeUniversitaire": 1,
                      "departement": "Mathématiques"
                    }
                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/utilisateurs/search")
                        .param("valeur", "Bernard"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nom").value("Bernard"));
    }
}