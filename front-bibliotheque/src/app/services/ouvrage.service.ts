import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ouvrage } from '../models/ouvrage.model';
import {apiUrl,apiOuvrages} from '../../constante';

@Injectable({ providedIn: 'root' })
export class OuvrageService {
  private http = inject(HttpClient);

  private api = apiUrl + apiOuvrages;

  findAll(): Observable<Ouvrage[]> {
    return this.http.get<Ouvrage[]>(this.api);
  }

  search(criteria: {
    titre?: string;
    auteur?: string;
    anneePublication?: number | null;
    theme?: string;
  }): Observable<Ouvrage[]> {
    let params = new HttpParams();

    if (criteria.titre) params = params.set('titre', criteria.titre);
    if (criteria.auteur) params = params.set('auteur', criteria.auteur);
    if (criteria.anneePublication)
      params = params.set('anneePublication', criteria.anneePublication);
    if (criteria.theme) params = params.set('theme', criteria.theme);

    return this.http.get<Ouvrage[]>(`${this.api}/search`, { params });
  }

  createLivre(request: unknown): Observable<Ouvrage> {
    return this.http.post<Ouvrage>(`${this.api}/livres`, request);
  }

  createRevue(request: unknown): Observable<Ouvrage> {
    return this.http.post<Ouvrage>(`${this.api}/revues`, request);
  }
  findById(id: number) {
    return this.http.get<Ouvrage>(`${this.api}/${id}`);
  }
}
