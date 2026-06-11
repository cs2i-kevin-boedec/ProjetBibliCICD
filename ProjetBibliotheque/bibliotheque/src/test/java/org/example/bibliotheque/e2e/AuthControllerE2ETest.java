package org.example.bibliotheque.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerE2ETest extends BaseE2ETest {

    @Test
    void shouldLoginSuccessfully() throws Exception {
        mockMvc.perform(post("/api/utilisateurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "typeUtilisateur": "ETUDIANT",
                      "nom": "Admin",
                      "prenom": "Root",
                      "adresse": "1 rue de Paris",
                      "email": "admin@test.com",
                      "login": "admin",
                      "motDePasse": "Admin123!",
                      "cautionInitiale": 100.0,
                      "anneeUniversitaire": 3,
                      "departement": "Informatique"
                    }
                """))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "login": "admin",
                      "motDePasse": "Admin123!"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldChangePassword() throws Exception {
        mockMvc.perform(post("/api/utilisateurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "typeUtilisateur": "ETUDIANT",
                      "nom": "Test",
                      "prenom": "User",
                      "adresse": "2 avenue de Lyon",
                      "email": "tuser@test.com",
                      "login": "tuser",
                      "motDePasse": "OldPass123!",
                      "cautionInitiale": 80.0,
                      "anneeUniversitaire": 2,
                      "departement": "Informatique"
                    }
                """))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "login": "tuser",
                      "ancienMotDePasse": "OldPass123!",
                      "nouveauMotDePasse": "NewPass123!"
                    }
                """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "login": "tuser",
                      "motDePasse": "NewPass123!"
                    }
                """))
                .andExpect(status().isOk());
    }
}