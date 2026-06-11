import { Component, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Emprunt } from '../../../../models/emprunt.model';
import { Utilisateur } from '../../../../models/utilisateur.model';
import { EmpruntService } from '../../../../services/emprunt.service';
import { UtilisateurService } from '../../../../services/utilisateur.service';

@Component({
  selector: 'app-retour-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './retour-create.html',
  styleUrl: './retour-create.css',
})
export class RetourCreateComponent {
  private utilisateurService = inject(UtilisateurService);
  private empruntService = inject(EmpruntService);

  rechercheUtilisateur = '';
  utilisateurs = signal<Utilisateur[]>([]);
  utilisateurSelectionneId: number | null = null;

  emprunts = signal<Emprunt[]>([]);
  empruntId: number | null = null;
  empruntSelectionne = signal<Emprunt | null>(null);

  etatExemplaire = 'BON';
  message = signal<string>('');

  rechercherUtilisateurs(): void {
    if (!this.rechercheUtilisateur.trim()) {
      this.message.set('Veuillez saisir un nom ou un prénom');
      return;
    }

    this.message.set('');
    this.utilisateurSelectionneId = null;
    this.emprunts.set([]);
    this.empruntId = null;
    this.empruntSelectionne.set(null);

    this.utilisateurService.searchByNomOuPrenom(this.rechercheUtilisateur).subscribe({
      next: (data) => {
        this.utilisateurs.set(data);
        if (data.length === 0) {
          this.message.set('Aucun utilisateur trouvé');
        }
      },
      error: (err) => {
        this.message.set(err?.error?.error ?? 'Erreur recherche utilisateur');
      },
    });
  }

  chargerEmpruntsActifs(): void {
    if (!this.utilisateurSelectionneId) {
      this.message.set('Veuillez sélectionner un utilisateur');
      return;
    }

    this.message.set('');
    this.empruntId = null;
    this.empruntSelectionne.set(null);

    this.empruntService.findActifsByUtilisateur(this.utilisateurSelectionneId).subscribe({
      next: (data) => {
        this.emprunts.set(data);
        if (data.length === 0) {
          this.message.set('Aucun emprunt actif pour cet utilisateur');
        }
      },
      error: (err) => {
        this.message.set(err?.error?.error ?? 'Erreur chargement emprunts');
      },
    });
  }

  onEmpruntChange(): void {
    const selected = this.emprunts().find((e) => e.id === Number(this.empruntId)) ?? null;
    this.empruntSelectionne.set(selected);
  }

  onSubmit(): void {
    if (!this.empruntId) {
      this.message.set('Veuillez sélectionner un ouvrage à retourner');
      return;
    }

    this.empruntService
      .retourner({
        empruntId: this.empruntId,
        etatExemplaire: this.etatExemplaire,
      })
      .subscribe({
        next: () => {
          this.message.set('Retour enregistré');
          this.emprunts.set(this.emprunts().filter((e) => e.id !== this.empruntId));
          this.empruntId = null;
          this.empruntSelectionne.set(null);
          this.etatExemplaire = 'BON';
        },
        error: (err) => {
          this.message.set(err?.error?.error ?? 'Erreur retour');
        },
      });
  }
}
