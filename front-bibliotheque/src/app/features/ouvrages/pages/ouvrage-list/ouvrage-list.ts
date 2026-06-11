import { Component, OnInit, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Ouvrage } from '../../../../models/ouvrage.model';
import { AuthService } from '../../../../services/auth.service';
import { EmpruntService } from '../../../../services/emprunt.service';
import { OuvrageService } from '../../../../services/ouvrage.service';
import { ReservationService } from '../../../../services/reservation.service';
import { UtilisateurService } from '../../../../services/utilisateur.service';

@Component({
  selector: 'app-ouvrage-list',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './ouvrage-list.html',
  styleUrl: './ouvrage-list.css',
})
export class OuvrageListComponent implements OnInit {
  authService = inject(AuthService);
  private ouvrageService = inject(OuvrageService);
  private utilisateurService = inject(UtilisateurService);
  private empruntService = inject(EmpruntService);
  private reservationService = inject(ReservationService);
  private router = inject(Router);

  ouvrages = signal<Ouvrage[]>([]);
  titre = '';
  auteur = '';
  anneePublication: number | null = null;
  theme = '';
  message = signal<string>('');

  ngOnInit(): void {
    this.chargerTous();
  }

  chargerTous(): void {
    this.ouvrageService.findAll().subscribe((data) => this.ouvrages.set(data));
  }

  rechercher(): void {
    this.ouvrageService
      .search({
        titre: this.titre,
        auteur: this.auteur,
        anneePublication: this.anneePublication,
        theme: this.theme,
      })
      .subscribe((data) => this.ouvrages.set(data));
  }

  resetRecherche(): void {
    this.titre = '';
    this.auteur = '';
    this.anneePublication = null;
    this.theme = '';
    this.chargerTous();
  }

  emprunter(ouvrage: Ouvrage): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }

    if (!this.authService.isUtilisateur()) {
      this.message.set('Seul un utilisateur peut emprunter un ouvrage.');
      return;
    }

    const confirmation = window.confirm(`Confirmer l'emprunt de "${ouvrage.titre}" ?`);
    if (!confirmation) {
      return;
    }

    const login = this.authService.currentLogin();
    if (!login) {
      this.message.set('Utilisateur non connecté.');
      return;
    }

    this.utilisateurService.findByLogin(login).subscribe({
      next: (utilisateur) => {
        this.empruntService
          .create({
            utilisateurId: utilisateur.id,
            ouvrageId: ouvrage.id,
          })
          .subscribe({
            next: () => {
              this.message.set(`Emprunt de "${ouvrage.titre}" enregistré.`);
              this.chargerTous();
            },
            error: (err) => {
              this.message.set(err?.error?.error ?? 'Erreur lors de l’emprunt');
            },
          });
      },
      error: () => {
        this.message.set('Impossible d’identifier l’utilisateur connecté.');
      },
    });
  }

  reserver(ouvrage: Ouvrage): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }

    if (!this.authService.isUtilisateur()) {
      this.message.set('Seul un utilisateur peut réserver un ouvrage.');
      return;
    }

    const confirmation = window.confirm(`Confirmer la réservation de "${ouvrage.titre}" ?`);
    if (!confirmation) {
      return;
    }

    const login = this.authService.currentLogin();
    if (!login) {
      this.message.set('Utilisateur non connecté.');
      return;
    }

    this.utilisateurService.findByLogin(login).subscribe({
      next: (utilisateur) => {
        this.reservationService
          .create({
            utilisateurId: utilisateur.id,
            ouvrageId: ouvrage.id,
          })
          .subscribe({
            next: () => {
              this.message.set(`Réservation de "${ouvrage.titre}" enregistrée.`);
            },
            error: (err) => {
              this.message.set(err?.error?.error ?? 'Erreur lors de la réservation');
            },
          });
      },
      error: () => {
        this.message.set('Impossible d’identifier l’utilisateur connecté.');
      },
    });
  }
}
