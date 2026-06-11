import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Emprunt } from '../models/emprunt.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RetardService {
  private readonly api = 'http://localhost:8080/api/retards';
  private readonly http = inject(HttpClient);

  getRetards(): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(this.api);
  }

  envoyerRelances(): Observable<void> {
    return this.http.post<void>(`${this.api}/relances`, {});
  }
}