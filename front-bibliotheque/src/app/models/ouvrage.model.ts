export interface Ouvrage {
  id: number;
  type: string;
  titre: string;
  auteur?: string | null;
  isbn?: string | null;
  anneePublication?: number | null;
  numeroVolume?: number | null;
  dateParution?: string | null;
  theme: string;
  caution: number;
  nombreExemplairesDisponibles: number;
}