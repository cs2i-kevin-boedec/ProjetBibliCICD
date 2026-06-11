package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.enums.TypeUtilisateur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurFactoryProviderTest {

    @Test
    void shouldReturnEtudiantFactory() {
        EtudiantFactory etudiantFactory = new EtudiantFactory();
        EnseignantFactory enseignantFactory = new EnseignantFactory();
        ParticulierFactory particulierFactory = new ParticulierFactory();

        UtilisateurFactoryProvider provider = new UtilisateurFactoryProvider(
                etudiantFactory,
                enseignantFactory,
                particulierFactory
        );

        UtilisateurFactory result = provider.getFactory(TypeUtilisateur.ETUDIANT);

        assertSame(etudiantFactory, result);
    }

    @Test
    void shouldReturnEnseignantFactory() {
        EtudiantFactory etudiantFactory = new EtudiantFactory();
        EnseignantFactory enseignantFactory = new EnseignantFactory();
        ParticulierFactory particulierFactory = new ParticulierFactory();

        UtilisateurFactoryProvider provider = new UtilisateurFactoryProvider(
                etudiantFactory,
                enseignantFactory,
                particulierFactory
        );

        UtilisateurFactory result = provider.getFactory(TypeUtilisateur.ENSEIGNANT);

        assertSame(enseignantFactory, result);
    }

    @Test
    void shouldReturnParticulierFactory() {
        EtudiantFactory etudiantFactory = new EtudiantFactory();
        EnseignantFactory enseignantFactory = new EnseignantFactory();
        ParticulierFactory particulierFactory = new ParticulierFactory();

        UtilisateurFactoryProvider provider = new UtilisateurFactoryProvider(
                etudiantFactory,
                enseignantFactory,
                particulierFactory
        );

        UtilisateurFactory result = provider.getFactory(TypeUtilisateur.PARTICULIER);

        assertSame(particulierFactory, result);
    }
}