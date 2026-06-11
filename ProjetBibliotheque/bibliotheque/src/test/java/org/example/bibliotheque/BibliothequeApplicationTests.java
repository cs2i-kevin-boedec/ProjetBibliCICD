package org.example.bibliotheque;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BibliothequeApplicationTests {

    @Test
    void contextLoads() {
    }

}
