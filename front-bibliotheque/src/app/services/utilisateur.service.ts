import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Utilisateur } from '../models/utilisateur.model';
import { apiUtilisateurs } from '../../constante';

@Injectable({ providedIn: 'root' })
export class UtilisateurService {
  private api = apiUtilisateurs;
  private http = inject(HttpClient);

  create(request: unknown): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(this.api, request);
  }

  findAll(): Observable<Utilisateur[]> {
    return this.http.get<Utilisateur[]>(this.api);
  }

  findById(id: number): Observable<Utilisateur> {
    return this.http.get<Utilisateur>(`${this.api}/${id}`);
  }

  findByLogin(login: string): Observable<Utilisateur> {
    return this.http.get<Utilisateur>(`${this.api}/by-login/${login}`);
  }

  searchByNomOuPrenom(valeur: string): Observable<Utilisateur[]> {
    return this.http.get<Utilisateur[]>(`${this.api}/search`, {
      params: { valeur }
    });
  }
}