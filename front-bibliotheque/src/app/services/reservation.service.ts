import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation.model';
import {apiUrl,apiReservations} from '../../constante';

@Injectable({ providedIn: 'root' })
export class ReservationService {
  private http = inject(HttpClient);

  private api = apiUrl + apiReservations;

  create(request: { utilisateurId: number; ouvrageId: number }): Observable<Reservation> {
    return this.http.post<Reservation>(this.api, request);
  }
}
