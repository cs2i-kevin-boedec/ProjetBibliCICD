import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { EmpruntService } from './emprunt.service';
import { Emprunt } from '../models/emprunt.model';

describe('EmpruntService', () => {
  let service: EmpruntService;
  let httpMock: HttpTestingController;

  const api = 'http://localhost:8080/api';

  const mockEmprunt: Emprunt = {
    id: 1,
  } as Emprunt;

  const mockEmprunts: Emprunt[] = [
    { id: 1 } as Emprunt,
    { id: 2 } as Emprunt,
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        EmpruntService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(EmpruntService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('doit être créé', () => {
    expect(service).toBeTruthy();
  });

  it('create() doit envoyer un POST vers /emprunts', () => {
    const requestBody = {
      utilisateurId: 1,
      ouvrageId: 10,
    };

    service.create(requestBody).subscribe((result) => {
      expect(result).toEqual(mockEmprunt);
    });

    const req = httpMock.expectOne(`${api}/emprunts`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(requestBody);

    req.flush(mockEmprunt);
  });

  it('findByUtilisateur() doit envoyer un GET vers /emprunts/utilisateur/:id', () => {
    service.findByUtilisateur(1).subscribe((result) => {
      expect(result).toEqual(mockEmprunts);
      expect(result.length).toBe(2);
    });

    const req = httpMock.expectOne(`${api}/emprunts/utilisateur/1`);
    expect(req.request.method).toBe('GET');

    req.flush(mockEmprunts);
  });

  it('retourner() doit envoyer un POST vers /retours', () => {
    const requestBody = {
      empruntId: 1,
      etatExemplaire: 'BON',
    };

    service.retourner(requestBody).subscribe((result) => {
      expect(result).toEqual(mockEmprunt);
    });

    const req = httpMock.expectOne(`${api}/retours`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(requestBody);

    req.flush(mockEmprunt);
  });

  it('findRetards() doit envoyer un GET vers /retards', () => {
    service.findRetards().subscribe((result) => {
      expect(result).toEqual(mockEmprunts);
      expect(result.length).toBe(2);
    });

    const req = httpMock.expectOne(`${api}/retards`);
    expect(req.request.method).toBe('GET');

    req.flush(mockEmprunts);
  });

  it('envoyerRelances() doit envoyer un POST vers /retards/relances avec un body vide', () => {
    service.envoyerRelances().subscribe((result) => {
      expect(result).toBeNull();
    });

    const req = httpMock.expectOne(`${api}/retards/relances`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({});

    req.flush(null);
  });

  it('findActifsByUtilisateur() doit envoyer un GET vers /emprunts/utilisateur/:id/actifs', () => {
    service.findActifsByUtilisateur(1).subscribe((result) => {
      expect(result).toEqual(mockEmprunts);
      expect(result.length).toBe(2);
    });

    const req = httpMock.expectOne(`${api}/emprunts/utilisateur/1/actifs`);
    expect(req.request.method).toBe('GET');

    req.flush(mockEmprunts);
  });
});