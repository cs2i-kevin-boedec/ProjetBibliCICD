import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { OuvrageService } from './ouvrage.service';
import { Ouvrage } from '../models/ouvrage.model';

describe('OuvrageService', () => {
  let service: OuvrageService;
  let httpMock: HttpTestingController;

  const api = 'http://localhost:8080/api/ouvrages';

  const mockOuvrage: Ouvrage = {
    id: 1,
  } as Ouvrage;

  const mockOuvrages: Ouvrage[] = [
    mockOuvrage,
    { id: 2 } as Ouvrage,
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        OuvrageService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(OuvrageService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('doit être créé', () => {
    expect(service).toBeTruthy();
  });

  it('findAll() doit envoyer un GET et retourner la liste', () => {
    service.findAll().subscribe((result) => {
      expect(result).toEqual(mockOuvrages);
      expect(result.length).toBe(2);
    });

    const req = httpMock.expectOne(api);
    expect(req.request.method).toBe('GET');

    req.flush(mockOuvrages);
  });

  it('search() doit envoyer un GET avec les bons query params', () => {
    const criteria = {
      titre: 'Angular',
      auteur: 'Dupont',
      anneePublication: 2024,
      theme: 'Informatique',
    };

    service.search(criteria).subscribe((result) => {
      expect(result).toEqual(mockOuvrages);
    });

    const req = httpMock.expectOne((request) => {
      return (
        request.url === `${api}/search` &&
        request.method === 'GET' &&
        request.params.get('titre') === 'Angular' &&
        request.params.get('auteur') === 'Dupont' &&
        request.params.get('anneePublication') === '2024' &&
        request.params.get('theme') === 'Informatique'
      );
    });

    req.flush(mockOuvrages);
  });

  it('search() ne doit envoyer que les params définis', () => {
    const criteria = {
      titre: 'Angular',
    };

    service.search(criteria).subscribe();

    const req = httpMock.expectOne((request) => {
      return (
        request.url === `${api}/search` &&
        request.params.get('titre') === 'Angular' &&
        request.params.keys().length === 1
      );
    });

    expect(req.request.method).toBe('GET');

    req.flush([]);
  });

  it('createLivre() doit envoyer un POST vers /livres', () => {
    const body = { titre: 'Livre test' };

    service.createLivre(body).subscribe((result) => {
      expect(result).toEqual(mockOuvrage);
    });

    const req = httpMock.expectOne(`${api}/livres`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(body);

    req.flush(mockOuvrage);
  });

  it('createRevue() doit envoyer un POST vers /revues', () => {
    const body = { titre: 'Revue test' };

    service.createRevue(body).subscribe((result) => {
      expect(result).toEqual(mockOuvrage);
    });

    const req = httpMock.expectOne(`${api}/revues`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(body);

    req.flush(mockOuvrage);
  });

  it('findById() doit envoyer un GET vers /:id', () => {
    service.findById(1).subscribe((result) => {
      expect(result).toEqual(mockOuvrage);
    });

    const req = httpMock.expectOne(`${api}/1`);
    expect(req.request.method).toBe('GET');

    req.flush(mockOuvrage);
  });
});