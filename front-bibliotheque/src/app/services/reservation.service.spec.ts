import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { ReservationService } from './reservation.service';
import { Reservation } from '../models/reservation.model';

describe('ReservationService', () => {
  let service: ReservationService;
  let httpMock: HttpTestingController;

  const api = 'http://localhost:8080/api/reservations';

  const mockReservation: Reservation = {
    id: 1,
  } as Reservation;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ReservationService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(ReservationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('doit être créé', () => {
    expect(service).toBeTruthy();
  });

  it('create() doit envoyer un POST avec utilisateurId et ouvrageId', () => {
    const requestBody = {
      utilisateurId: 1,
      ouvrageId: 10,
    };

    service.create(requestBody).subscribe((result) => {
      expect(result).toEqual(mockReservation);
    });

    const req = httpMock.expectOne(api);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(requestBody);

    req.flush(mockReservation);
  });
});