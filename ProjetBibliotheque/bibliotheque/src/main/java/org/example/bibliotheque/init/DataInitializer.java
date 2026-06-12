package org.example.bibliotheque.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bibliotheque.entity.*;
import org.example.bibliotheque.enums.EtatExemplaire;
import org.example.bibliotheque.enums.StatutEmprunt;
import org.example.bibliotheque.enums.StatutReservation;
import org.example.bibliotheque.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final BibliothecaireRepository bibliothecaireRepository;
    private final CompteRepository compteRepository;
    private final EmplacementStockageRepository emplacementRepository;
    private final OuvrageRepository ouvrageRepository;
    private final ExemplaireRepository exemplaireRepository;
    private final EmpruntRepository empruntRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) {
        log.info("DataInitializer starting: utilisateur count={}, ouvrage count={}", utilisateurRepository.count(), ouvrageRepository.count());
        if (utilisateurRepository.count() > 0 || ouvrageRepository.count() > 0) {
            log.info("Data already present, skipping initialization.");
            return;
        }

        // Bibliothécaire
        Bibliothecaire bibliothecaire = new Bibliothecaire();
        bibliothecaire.setNom("Admin");
        bibliothecaire.setPrenom("Biblio");
        bibliothecaire.setEmail("bibliothecaire@univ.fr");
        bibliothecaire.setLogin("biblio");
        bibliothecaire.setMotDePasse("biblio123");
        bibliothecaire.setPremiereConnexion(false);
        bibliothecaireRepository.save(bibliothecaire);

        // Utilisateur 1 : Etudiant
        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Dupont");
        etudiant.setPrenom("Alice");
        etudiant.setAdresse("10 rue des Lilas");
        etudiant.setEmail("alice.dupont@univ.fr");
        etudiant.setLogin("alice");
        etudiant.setMotDePasse("alice123");
        etudiant.setPremiereConnexion(false);
        etudiant.setAnneeUniversitaire(3);
        utilisateurRepository.save(etudiant);

        Compte compteEtudiant = new Compte();
        compteEtudiant.setUtilisateur(etudiant);
        compteEtudiant.setCautionDisponible(120.0);
        compteRepository.save(compteEtudiant);
        etudiant.setCompte(compteEtudiant);

        // Utilisateur 2 : Enseignant
        Enseignant enseignant = new Enseignant();
        enseignant.setNom("Martin");
        enseignant.setPrenom("Paul");
        enseignant.setAdresse("20 avenue Victor Hugo");
        enseignant.setEmail("paul.martin@univ.fr");
        enseignant.setLogin("pmartin");
        enseignant.setMotDePasse("paul123");
        enseignant.setPremiereConnexion(false);
        enseignant.setDepartement("Informatique");
        utilisateurRepository.save(enseignant);

        Compte compteEnseignant = new Compte();
        compteEnseignant.setUtilisateur(enseignant);
        compteEnseignant.setCautionDisponible(200.0);
        compteRepository.save(compteEnseignant);
        enseignant.setCompte(compteEnseignant);

        // Utilisateur 3 : Particulier
        Particulier particulier = new Particulier();
        particulier.setNom("Bernard");
        particulier.setPrenom("Julie");
        particulier.setAdresse("5 place de la Gare");
        particulier.setEmail("julie.bernard@test.fr");
        particulier.setLogin("julie");
        particulier.setMotDePasse("julie123");
        particulier.setPremiereConnexion(false);
        utilisateurRepository.save(particulier);

        Compte compteParticulier = new Compte();
        compteParticulier.setUtilisateur(particulier);
        compteParticulier.setCautionDisponible(80.0);
        compteRepository.save(compteParticulier);
        particulier.setCompte(compteParticulier);

        // Emplacements
        EmplacementStockage emplacementInfo = new EmplacementStockage();
        emplacementInfo.setTravee(1);
        emplacementInfo.setEtagere(2);
        emplacementInfo.setNiveau("A");
        emplacementInfo.setCategorie("programmation");
        emplacementRepository.save(emplacementInfo);

        EmplacementStockage emplacementRoman = new EmplacementStockage();
        emplacementRoman.setTravee(2);
        emplacementRoman.setEtagere(1);
        emplacementRoman.setNiveau("B");
        emplacementRoman.setCategorie("roman");
        emplacementRepository.save(emplacementRoman);

        EmplacementStockage emplacementRevue = new EmplacementStockage();
        emplacementRevue.setTravee(3);
        emplacementRevue.setEtagere(1);
        emplacementRevue.setNiveau("C");
        emplacementRevue.setCategorie("revue");
        emplacementRepository.save(emplacementRevue);

        // Livre 1
        Livre livre1 = new Livre();
        livre1.setTitre("Java propre");
        livre1.setAuteur("Robert Martin");
        livre1.setIsbn("9780132350884");
        livre1.setAnneePublication(2008);
        livre1.setTheme("informatique");
        livre1.setCaution(20.0);
        livre1.setEmplacementStockage(emplacementInfo);
        ouvrageRepository.save(livre1);

        // Livre 2
        Livre livre2 = new Livre();
        livre2.setTitre("Clean Architecture");
        livre2.setAuteur("Robert Martin");
        livre2.setIsbn("9780134494166");
        livre2.setAnneePublication(2017);
        livre2.setTheme("informatique");
        livre2.setCaution(25.0);
        livre2.setEmplacementStockage(emplacementInfo);
        ouvrageRepository.save(livre2);

        // Livre 3
        Livre livre3 = new Livre();
        livre3.setTitre("Le Comte de Monte-Cristo");
        livre3.setAuteur("Alexandre Dumas");
        livre3.setIsbn("9782070409228");
        livre3.setAnneePublication(1846);
        livre3.setTheme("roman");
        livre3.setCaution(15.0);
        livre3.setEmplacementStockage(emplacementRoman);
        ouvrageRepository.save(livre3);

        // Revue
        Revue revue1 = new Revue();
        revue1.setTitre("Science & Vie");
        revue1.setNumeroVolume(42);
        revue1.setDateParution(LocalDate.of(2025, 6, 15));
        revue1.setTheme("science");
        revue1.setCaution(10.0);
        revue1.setEmplacementStockage(emplacementRevue);
        ouvrageRepository.save(revue1);

        // Exemplaires
        createExemplaire(livre1, true);
        createExemplaire(livre1, true);
        createExemplaire(livre1, true);

        createExemplaire(livre2, true);
        createExemplaire(livre2, true);

        createExemplaire(livre3, true);
        createExemplaire(livre3, true);

        createExemplaire(revue1, true);
        createExemplaire(revue1, true);

        // Emprunt en cours
        Exemplaire exemplaireLivre1 = exemplaireRepository.findFirstByOuvrageIdAndDisponibleTrue(livre1.getId())
                .orElseThrow();
        exemplaireLivre1.setDisponible(false);
        exemplaireRepository.save(exemplaireLivre1);

        compteEtudiant.debiter(livre1.getCaution());
        compteRepository.save(compteEtudiant);

        Emprunt emprunt1 = new Emprunt();
        emprunt1.setUtilisateur(etudiant);
        emprunt1.setExemplaire(exemplaireLivre1);
        emprunt1.setDateEmprunt(LocalDate.now().minusDays(2));
        emprunt1.setDateRetourPrevue(LocalDate.now().plusDays(13));
        emprunt1.setStatut(StatutEmprunt.EN_COURS);
        empruntRepository.save(emprunt1);

        // Emprunt en retard
        Exemplaire exemplaireLivre3 = exemplaireRepository.findFirstByOuvrageIdAndDisponibleTrue(livre3.getId())
                .orElseThrow();
        exemplaireLivre3.setDisponible(false);
        exemplaireRepository.save(exemplaireLivre3);

        compteEnseignant.debiter(livre3.getCaution());
        compteRepository.save(compteEnseignant);

        Emprunt emprunt2 = new Emprunt();
        emprunt2.setUtilisateur(enseignant);
        emprunt2.setExemplaire(exemplaireLivre3);
        emprunt2.setDateEmprunt(LocalDate.now().minusDays(20));
        emprunt2.setDateRetourPrevue(LocalDate.now().minusDays(5));
        emprunt2.setStatut(StatutEmprunt.EN_COURS);
        empruntRepository.save(emprunt2);

        // Réservation
        Reservation reservation = new Reservation();
        reservation.setUtilisateur(particulier);
        reservation.setOuvrage(revue1);
        reservation.setDateReservation(LocalDate.now());
        reservation.setStatut(StatutReservation.ACTIVE);
        reservationRepository.save(reservation);
        log.info("DataInitializer finished: inserted sample users={}, ouvrages={}", utilisateurRepository.count(), ouvrageRepository.count());
    }

    private void createExemplaire(Ouvrage ouvrage, boolean disponible) {
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setCodeBarre(UUID.randomUUID().toString());
        exemplaire.setDisponible(disponible);
        exemplaire.setEtat(EtatExemplaire.BON);
        exemplaire.setOuvrage(ouvrage);
        exemplaireRepository.save(exemplaire);
    }
}