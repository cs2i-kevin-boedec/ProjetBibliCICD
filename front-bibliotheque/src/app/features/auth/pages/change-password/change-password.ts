import { Component, OnInit, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css',
})
export class ChangePasswordComponent implements OnInit {
  private authService = inject(AuthService);
  private router = inject(Router);

  login = '';
  ancienMotDePasse = '';
  nouveauMotDePasse = '';
  message = signal<string>('');

  ngOnInit(): void {
    this.login = this.authService.currentLogin() ?? '';
  }

  onSubmit(): void {
    this.authService
      .changePassword({
        login: this.login,
        ancienMotDePasse: this.ancienMotDePasse,
        nouveauMotDePasse: this.nouveauMotDePasse,
      })
      .subscribe({
        next: () => {
          this.message.set('Mot de passe modifié');
          this.router.navigate(['/']);
        },
        error: (err) => this.message.set(err?.error?.error ?? 'Erreur de modification'),
      });
  }
}
