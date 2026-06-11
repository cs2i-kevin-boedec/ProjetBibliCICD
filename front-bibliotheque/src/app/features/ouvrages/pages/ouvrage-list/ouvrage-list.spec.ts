import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';

import { OuvrageListComponent } from './ouvrage-list';

describe('OuvrageListComponent', () => {
  let component: OuvrageListComponent;
  let fixture: ComponentFixture<OuvrageListComponent>;
  let httpMock: HttpTestingController;

  const api = 'http://localhost:8080/api/ouvrages';

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OuvrageListComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(OuvrageListComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);

    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    const req = httpMock.expectOne(api);
    expect(req.request.method).toBe('GET');
    req.flush([]);

    expect(component).toBeTruthy();
  });
});