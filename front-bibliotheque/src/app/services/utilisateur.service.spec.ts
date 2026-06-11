import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { UtilisateurService } from './utilisateur.service';
import { Utilisateur } from '../models/utilisateur.model';

describe('UtilisateurService', () => {
  let service: UtilisateurService;
  let httpMock: HttpTestingController;

  const api = 'http://localhost:8080/api/utilisateurs';

  const mockUtilisateur: Utilisateur = {
    id: 1,
    nom: 'Dupont',
    prenom: 'Jean',
    login: 'jdupont',
  } as Utilisateur;

  const mockUtilisateurs: Utilisateur[] = [
    mockUtilisateur,
    {
      id: 2,
      nom: 'Martin',
      prenom: 'Claire',
      login: 'cmartin',
    } as Utilisateur,
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UtilisateurService,
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    });

    service = TestBed.inject(UtilisateurService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('doit être créé', () => {
    expect(service).toBeTruthy();
  });

  it('create() doit envoyer un POST et retourner un utilisateur', () => {
    const requestBody = {
      nom: 'Dupont',
      prenom: 'Jean',
      login: 'jdupont',
    };

    service.create(requestBody).subscribe((result) => {
      expect(result).toEqual(mockUtilisateur);
    });

    const req = httpMock.expectOne(api);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(requestBody);

    req.flush(mockUtilisateur);
  });

  it('findAll() doit envoyer un GET et retourner la liste des utilisateurs', () => {
    service.findAll().subscribe((result) => {
      expect(result).toEqual(mockUtilisateurs);
      expect(result.length).toBe(2);
    });

    const req = httpMock.expectOne(api);
    expect(req.request.method).toBe('GET');

    req.flush(mockUtilisateurs);
  });

  it('findById() doit envoyer un GET vers /:id', () => {
    service.findById(1).subscribe((result) => {
      expect(result).toEqual(mockUtilisateur);
    });

    const req = httpMock.expectOne(`${api}/1`);
    expect(req.request.method).toBe('GET');

    req.flush(mockUtilisateur);
  });

  it('findByLogin() doit envoyer un GET vers /by-login/:login', () => {
    service.findByLogin('jdupont').subscribe((result) => {
      expect(result).toEqual(mockUtilisateur);
    });

    const req = httpMock.expectOne(`${api}/by-login/jdupont`);
    expect(req.request.method).toBe('GET');

    req.flush(mockUtilisateur);
  });

  it('searchByNomOuPrenom() doit envoyer un GET vers /search avec le paramètre valeur', () => {
    service.searchByNomOuPrenom('Jean').subscribe((result) => {
      expect(result).toEqual(mockUtilisateurs);
    });

    const req = httpMock.expectOne((request) => {
      return (
        request.url === `${api}/search` &&
        request.method === 'GET' &&
        request.params.get('valeur') === 'Jean'
      );
    });

    expect(req.request.method).toBe('GET');

    req.flush(mockUtilisateurs);
  });
});