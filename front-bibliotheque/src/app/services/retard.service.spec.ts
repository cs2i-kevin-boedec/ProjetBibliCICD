import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { RetardService } from './retard.service';
import { Emprunt } from '../models/emprunt.model';

describe('RetardService', () => {
  let service: RetardService;
  let httpMock: HttpTestingController;

  const api = 'http://localhost:8080/api/retards';

  const mockRetards: Emprunt[] = [
    {
      id: 1,
    } as Emprunt,
    {
      id: 2,
    } as Emprunt,
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        RetardService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(RetardService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('doit être créé', () => {
    expect(service).toBeTruthy();
  });

  it('getRetards() doit envoyer un GET et retourner la liste des retards', () => {
    service.getRetards().subscribe((result) => {
      expect(result).toEqual(mockRetards);
      expect(result.length).toBe(2);
    });

    const req = httpMock.expectOne(api);
    expect(req.request.method).toBe('GET');

    req.flush(mockRetards);
  });

  it('envoyerRelances() doit envoyer un POST vers /relances', () => {
    service.envoyerRelances().subscribe((result) => {
      expect(result).toBeNull(); // void
    });

    const req = httpMock.expectOne(`${api}/relances`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({});

    req.flush(null);
  });
});