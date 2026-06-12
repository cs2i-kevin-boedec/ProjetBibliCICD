import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Emprunt } from '../models/emprunt.model';
import {apiEmprunts} from '../../constante';

@Injectable({ providedIn: 'root' })
export class EmpruntService {
  private http = inject(HttpClient);

  private api =  apiEmprunts;

  create(request: { utilisateurId: number; ouvrageId: number }): Observable<Emprunt> {
    return this.http.post<Emprunt>(`${this.api}/emprunts`, request);
  }

  findByUtilisateur(utilisateurId: number): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(`${this.api}/emprunts/utilisateur/${utilisateurId}`);
  }

  retourner(request: { empruntId: number; etatExemplaire: string }): Observable<Emprunt> {
    return this.http.post<Emprunt>(`${this.api}/retours`, request);
  }

  findRetards(): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(`${this.api}/retards`);
  }

  envoyerRelances(): Observable<void> {
    return this.http.post<void>(`${this.api}/retards/relances`, {});
  }
  findActifsByUtilisateur(utilisateurId: number) {
    return this.http.get<Emprunt[]>(`${this.api}/emprunts/utilisateur/${utilisateurId}/actifs`);
  }
}
