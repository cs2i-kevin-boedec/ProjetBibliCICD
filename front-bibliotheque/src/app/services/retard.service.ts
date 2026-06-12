import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Emprunt } from '../models/emprunt.model';
import { Observable } from 'rxjs';
import {apiRetards} from '../../constante';

@Injectable({ providedIn: 'root' })
export class RetardService {
  private readonly api =  apiRetards;
  private readonly http = inject(HttpClient);

  getRetards(): Observable<Emprunt[]> {
    return this.http.get<Emprunt[]>(this.api);
  }

  envoyerRelances(): Observable<void> {
    return this.http.post<void>(`${this.api}/relances`, {});
  }
}