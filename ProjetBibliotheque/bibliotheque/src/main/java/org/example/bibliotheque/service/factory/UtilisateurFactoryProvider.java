package org.example.bibliotheque.service.factory;

import org.example.bibliotheque.enums.TypeUtilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilisateurFactoryProvider {
    private final EtudiantFactory etudiantFactory;
    private final EnseignantFactory enseignantFactory;
    private final ParticulierFactory particulierFactory;

    public UtilisateurFactory getFactory(TypeUtilisateur type) {
        return switch (type) {
            case ETUDIANT -> etudiantFactory;
            case ENSEIGNANT -> enseignantFactory;
            case PARTICULIER -> particulierFactory;
        };
    }
}