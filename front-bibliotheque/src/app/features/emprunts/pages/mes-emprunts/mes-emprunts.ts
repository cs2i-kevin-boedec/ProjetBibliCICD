import { Component, OnInit, signal, inject } from '@angular/core';
import { Emprunt } from '../../../../models/emprunt.model';
import { AuthService } from '../../../../services/auth.service';
import { EmpruntService } from '../../../../services/emprunt.service';
import { UtilisateurService } from '../../../../services/utilisateur.service';

@Component({
  selector: 'app-mes-emprunts',
  standalone: true,
  templateUrl: './mes-emprunts.html',
  styleUrl: './mes-emprunts.css',
})
export class MesEmpruntsComponent implements OnInit {
  private empruntService = inject(EmpruntService);
  private utilisateurService = inject(UtilisateurService);
  private authService = inject(AuthService);

  emprunts = signal<Emprunt[]>([]);
  message = signal<string>('');

  ngOnInit(): void {
    this.charger();
  }

  charger(): void {
    const login = this.authService.currentLogin();

    if (!login) {
      this.message.set('Aucun utilisateur connecté');
      return;
    }

    this.message.set('');

    this.utilisateurService.findByLogin(login).subscribe({
      next: (utilisateur) => {
        this.empruntService.findByUtilisateur(utilisateur.id).subscribe({
          next: (data) => this.emprunts.set(data),
          error: (err) => this.message.set(err?.error?.error ?? 'Erreur chargement emprunts'),
        });
      },
      error: (err) => {
        this.message.set(err?.error?.error ?? 'Utilisateur introuvable');
      },
    });
  }
}
