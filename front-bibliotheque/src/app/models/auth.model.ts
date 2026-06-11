export interface LoginRequest {
  login: string;
  motDePasse: string;
}

export interface LoginResponse {
  message: string;
  premiereConnexion: boolean;
  login: string;
  role: 'UTILISATEUR' | 'BIBLIOTHECAIRE';
}

export interface ChangePasswordRequest {
  login: string;
  ancienMotDePasse: string;
  nouveauMotDePasse: string;
}