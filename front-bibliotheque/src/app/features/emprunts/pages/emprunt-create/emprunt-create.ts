import { Component, OnInit, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { EmpruntService } from '../../../../services/emprunt.service';
import { OuvrageService } from '../../../../services/ouvrage.service';
import { UtilisateurService } from '../../../../services/utilisateur.service';
import { Ouvrage } from '../../../../models/ouvrage.model';
import { Utilisateur } from '../../../../models/utilisateur.model';

@Component({
  selector: 'app-emprunt-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './emprunt-create.html',
  styleUrl: './emprunt-create.css',
})
export class EmpruntCreateComponent implements OnInit {
  private empruntService = inject(EmpruntService);
  private ouvrageService = inject(OuvrageService);
  private utilisateurService = inject(UtilisateurService);
  private route = inject(ActivatedRoute);

  utilisateurId: number | null = null;
  ouvrageId: number | null = null;

  ouvrage = signal<Ouvrage | null>(null);
  utilisateurs = signal<Utilisateur[]>([]);
  message = signal<string>('');

  ngOnInit(): void {
    const ouvrageId = this.route.snapshot.queryParamMap.get('ouvrageId');
    if (ouvrageId) {
      this.ouvrageId = Number(ouvrageId);
      this.ouvrageService.findById(this.ouvrageId).subscribe({
        next: (data) => this.ouvrage.set(data),
        error: () => this.message.set('Ouvrage introuvable'),
      });
    }

    this.utilisateurService.findAll().subscribe({
      next: (data) => this.utilisateurs.set(data),
      error: () => this.message.set('Impossible de charger les utilisateurs'),
    });
  }

  onSubmit(): void {
    if (!this.utilisateurId || !this.ouvrageId) {
      this.message.set('Utilisateur et ouvrage obligatoires');
      return;
    }

    this.empruntService
      .create({
        utilisateurId: this.utilisateurId,
        ouvrageId: this.ouvrageId,
      })
      .subscribe({
        next: () => this.message.set('Emprunt enregistré'),
        error: (err) => this.message.set(err?.error?.error ?? 'Erreur emprunt'),
      });
  }
}
