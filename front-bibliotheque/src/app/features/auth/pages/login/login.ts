import { Component, signal, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  login = '';
  motDePasse = '';
  message = signal<string>('');

  onSubmit(): void {
    this.message.set('');

    this.authService
      .login({
        login: this.login,
        motDePasse: this.motDePasse,
      })
      .subscribe({
        next: (response) => {
          this.message.set(response.message);

          if (response.premiereConnexion) {
            this.router.navigate(['/change-password']);
            return;
          }

          this.router.navigate(['/']);
        },
        error: (err) => {
          this.message.set(err?.error?.error ?? 'Erreur de connexion');
        },
      });
  }
}
