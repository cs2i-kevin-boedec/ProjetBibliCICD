import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { AuthService } from './auth.service';
import {
  LoginRequest,
  LoginResponse,
  ChangePasswordRequest,
} from '../models/auth.model';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  const api = 'http://localhost:8080/api/auth';

  beforeEach(() => {
    localStorage.clear();

    TestBed.configureTestingModule({
      providers: [
        AuthService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('doit être créé', () => {
    expect(service).toBeTruthy();
  });

 
  it('login() doit envoyer un POST et mettre à jour le localStorage et les signals', () => {
    const request: LoginRequest = {
      login: 'jdupont',
      motDePasse: '1234',
    };

    const response: LoginResponse = {
      message: 'Connexion réussie',
      premiereConnexion: false,
      login: 'jdupont',
      role: 'UTILISATEUR',
    };

    service.login(request).subscribe((result) => {
      expect(result).toEqual(response);
    });

    const req = httpMock.expectOne(`${api}/login`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(request);

    req.flush(response);

    expect(localStorage.getItem('login')).toBe('jdupont');
    expect(localStorage.getItem('role')).toBe('UTILISATEUR');

    expect(service.currentLogin()).toBe('jdupont');
    expect(service.currentRole()).toBe('UTILISATEUR');
    expect(service.isLoggedIn()).toBe(true);
  });

  it('changePassword() doit envoyer un POST vers /change-password', () => {
    const request: ChangePasswordRequest = {
      login: 'jdupont',
      ancienMotDePasse: 'oldPassword',
      nouveauMotDePasse: 'newPassword',
    };

    service.changePassword(request).subscribe((result) => {
      expect(result).toBeNull();
    });

    const req = httpMock.expectOne(`${api}/change-password`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(request);

    req.flush(null);
  });

  it('logout() doit vider le localStorage et réinitialiser les signals', () => {
    localStorage.setItem('login', 'jdupont');
    localStorage.setItem('role', 'BIBLIOTHECAIRE');

    service.currentLogin.set('jdupont');
    service.currentRole.set('BIBLIOTHECAIRE');
    service.isLoggedIn.set(true);

    service.logout();

    expect(localStorage.getItem('login')).toBeNull();
    expect(localStorage.getItem('role')).toBeNull();

    expect(service.currentLogin()).toBeNull();
    expect(service.currentRole()).toBeNull();
    expect(service.isLoggedIn()).toBe(false);
  });

  it('isBibliothecaire() doit retourner true si le rôle est BIBLIOTHECAIRE', () => {
    service.currentRole.set('BIBLIOTHECAIRE');
    expect(service.isBibliothecaire()).toBe(true);

    service.currentRole.set('UTILISATEUR');
    expect(service.isBibliothecaire()).toBe(false);
  });

  it('isUtilisateur() doit retourner true si le rôle est UTILISATEUR', () => {
    service.currentRole.set('UTILISATEUR');
    expect(service.isUtilisateur()).toBe(true);

    service.currentRole.set('BIBLIOTHECAIRE');
    expect(service.isUtilisateur()).toBe(false);
  });
});