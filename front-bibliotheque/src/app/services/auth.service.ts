import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { ChangePasswordRequest, LoginRequest, LoginResponse } from '../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);

  private api = 'http://localhost:8080/api/auth';

  currentLogin = signal<string | null>(localStorage.getItem('login'));
  currentRole = signal<string | null>(localStorage.getItem('role'));
  isLoggedIn = signal<boolean>(!!localStorage.getItem('login'));

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api}/login`, request).pipe(
      tap((response) => {
        localStorage.setItem('login', response.login);
        localStorage.setItem('role', response.role);
        this.currentLogin.set(response.login);
        this.currentRole.set(response.role);
        this.isLoggedIn.set(true);
      }),
    );
  }

  changePassword(request: ChangePasswordRequest): Observable<void> {
    return this.http.post<void>(`${this.api}/change-password`, request);
  }

  logout(): void {
    localStorage.removeItem('login');
    localStorage.removeItem('role');
    this.currentLogin.set(null);
    this.currentRole.set(null);
    this.isLoggedIn.set(false);
  }

  isBibliothecaire(): boolean {
    return this.currentRole() === 'BIBLIOTHECAIRE';
  }

  isUtilisateur(): boolean {
    return this.currentRole() === 'UTILISATEUR';
  }
}
