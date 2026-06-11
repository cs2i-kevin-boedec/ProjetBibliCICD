import { Component, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { OuvrageService } from '../../../../services/ouvrage.service';

@Component({
  selector: 'app-livre-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './livre-create.html',
  styleUrl: './livre-create.css',
})
export class LivreCreateComponent {
  private ouvrageService = inject(OuvrageService);

  titre = '';
  auteur = '';
  isbn = '';
  anneePublication: number | null = null;
  theme = '';
  caution = 10;
  nombreExemplaires = 1;
  travee = 1;
  etagere = 1;
  niveau = 'A';
  categorie = 'programmation';
  message = signal<string>('');

  onSubmit(): void {
    this.ouvrageService
      .createLivre({
        titre: this.titre,
        auteur: this.auteur,
        isbn: this.isbn,
        anneePublication: this.anneePublication,
        theme: this.theme,
        caution: this.caution,
        nombreExemplaires: this.nombreExemplaires,
        travee: this.travee,
        etagere: this.etagere,
        niveau: this.niveau,
        categorie: this.categorie,
      })
      .subscribe({
        next: () => this.message.set('Livre ajouté avec succès'),
        error: (err) => this.message.set(err?.error?.error ?? "Erreur lors de l'ajout"),
      });
  }
}
