import { Component, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UtilisateurService } from '../../../../services/utilisateur.service';

@Component({
  selector: 'app-utilisateur-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './utilisateur-create.html',
  styleUrl: './utilisateur-create.css',
})
export class UtilisateurCreateComponent {
  private utilisateurService = inject(UtilisateurService);

  typeUtilisateur = 'ETUDIANT';
  nom = '';
  prenom = '';
  adresse = '';
  email = '';
  login = '';
  motDePasse = '';
  cautionInitiale = 100;
  anneeUniversitaire: number | null = 1;
  departement = '';
  message = signal<string>('');

  onSubmit(): void {
    this.utilisateurService
      .create({
        typeUtilisateur: this.typeUtilisateur,
        nom: this.nom,
        prenom: this.prenom,
        adresse: this.adresse,
        email: this.email,
        login: this.login,
        motDePasse: this.motDePasse,
        cautionInitiale: this.cautionInitiale,
        anneeUniversitaire: this.anneeUniversitaire,
        departement: this.departement,
      })
      .subscribe({
        next: () => this.message.set('Utilisateur créé avec succès'),
        error: (err) => this.message.set(err?.error?.error ?? 'Erreur de création'),
      });
  }
}
