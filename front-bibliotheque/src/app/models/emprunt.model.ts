export interface Emprunt {
  id: number;
  utilisateurNomComplet: string;
  ouvrageTitre: string;
  codeBarreExemplaire: string;
  dateEmprunt: string;
  dateRetourPrevue: string;
  dateRetourEffective?: string | null;
  statut: string;
}