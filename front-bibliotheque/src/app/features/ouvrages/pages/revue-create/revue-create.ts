import { Component, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { OuvrageService } from '../../../../services/ouvrage.service';

@Component({
  selector: 'app-revue-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './revue-create.html',
  styleUrl: './revue-create.css',
})
export class RevueCreateComponent {
  private ouvrageService = inject(OuvrageService);

  titre = '';
  numeroVolume: number | null = null;
  dateParution = '';
  theme = '';
  caution = 10;
  nombreExemplaires = 1;
  travee = 1;
  etagere = 1;
  niveau = 'A';
  categorie = 'revue';
  message = signal<string>('');

  onSubmit(): void {
    this.ouvrageService
      .createRevue({
        titre: this.titre,
        numeroVolume: this.numeroVolume,
        dateParution: this.dateParution,
        theme: this.theme,
        caution: this.caution,
        nombreExemplaires: this.nombreExemplaires,
        travee: this.travee,
        etagere: this.etagere,
        niveau: this.niveau,
        categorie: this.categorie,
      })
      .subscribe({
        next: () => this.message.set('Revue ajoutée avec succès'),
        error: (err) => this.message.set(err?.error?.error ?? "Erreur lors de l'ajout"),
      });
  }
}
