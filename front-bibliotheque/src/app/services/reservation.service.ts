import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation.model';

@Injectable({ providedIn: 'root' })
export class ReservationService {
  private http = inject(HttpClient);

  private api = 'http://localhost:8080/api/reservations';

  create(request: { utilisateurId: number; ouvrageId: number }): Observable<Reservation> {
    return this.http.post<Reservation>(this.api, request);
  }
}
