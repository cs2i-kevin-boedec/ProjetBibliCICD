import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { bibliothecaireGuard } from './core/guards/bibliothecaire.guard';

import { ChangePasswordComponent } from './features/auth/pages/change-password/change-password';
import { LoginComponent } from './features/auth/pages/login/login';
import { MesEmpruntsComponent } from './features/emprunts/pages/mes-emprunts/mes-emprunts';
import { RetardsListComponent } from './features/emprunts/pages/retards-list/retards-list';
import { RetourCreateComponent } from './features/emprunts/pages/retour-create/retour-create';
import { LivreCreateComponent } from './features/ouvrages/pages/livre-create/livre-create';
import { OuvrageListComponent } from './features/ouvrages/pages/ouvrage-list/ouvrage-list';
import { RevueCreateComponent } from './features/ouvrages/pages/revue-create/revue-create';
import { UtilisateurCreateComponent } from './features/utilisateurs/pages/utilisateur-create/utilisateur-create';

export const routes: Routes = [
  { path: '', component: OuvrageListComponent },
  { path: 'login', component: LoginComponent },
  { path: 'change-password', component: ChangePasswordComponent, canActivate: [authGuard] },
  

  { path: 'utilisateurs/create', component: UtilisateurCreateComponent, canActivate: [bibliothecaireGuard] },
  { path: 'ouvrages/livres/create', component: LivreCreateComponent, canActivate: [bibliothecaireGuard] },
  { path: 'ouvrages/revues/create', component: RevueCreateComponent, canActivate: [bibliothecaireGuard] },
  { path: 'retours/create', component: RetourCreateComponent, canActivate: [bibliothecaireGuard] },
  { path: 'retards', component: RetardsListComponent, canActivate: [bibliothecaireGuard] },

  { path: 'mes-emprunts', component: MesEmpruntsComponent, canActivate: [authGuard] },

  { path: '**', redirectTo: '' }
];