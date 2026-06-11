import { Component, OnInit, signal, inject } from '@angular/core';
import { RetardService } from '../../../../services/retard.service';
import { Emprunt } from '../../../../models/emprunt.model';

@Component({
  selector: 'app-retards-list',
  standalone: true,
  templateUrl: './retards-list.html',
  styleUrl: './retards-list.css',
})
export class RetardsListComponent implements OnInit {
  private retardService = inject(RetardService);

  retards = signal<Emprunt[]>([]);
  message = signal('');

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.retardService.getRetards().subscribe({
      next: (data) => this.retards.set(data),
      error: () => this.message.set('Erreur chargement retards'),
    });
  }

  envoyerRelances(): void {
    this.retardService.envoyerRelances().subscribe({
      next: () => this.message.set('Relances envoyées'),
      error: () => this.message.set('Erreur envoi relances'),
    });
  }
}
