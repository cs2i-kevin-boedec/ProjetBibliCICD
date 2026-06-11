#!/usr/bin/env bash
set -euo pipefail

if ! command -v docker >/dev/null 2>&1; then
  echo "Docker non installé ou non trouvé dans PATH. Installez Docker Desktop." >&2
  exit 1
fi

# Clonage automatique des dépôts si manquants
if ! command -v git >/dev/null 2>&1; then
  echo "Git non trouvé — si les dossiers front-bibliotheque ou ProjetBibliotheque manquent, clonez-les manuellement." >&2
else
  if [ ! -d "$DIR/front-bibliotheque" ]; then
    if [ -n "${FRONTEND_REPO:-}" ]; then
      git clone "$FRONTEND_REPO" "$DIR/front-bibliotheque"
    else
      read -p "URL du dépôt frontend (laisser vide pour ignorer): " url
      if [ -n "$url" ]; then
        git clone "$url" "$DIR/front-bibliotheque"
      else
        echo "Clonage frontend ignoré"
      fi
    fi
  fi

  if [ ! -d "$DIR/ProjetBibliotheque" ]; then
    if [ -n "${BACKEND_REPO:-}" ]; then
      git clone "$BACKEND_REPO" "$DIR/ProjetBibliotheque"
    else
      read -p "URL du dépôt backend (laisser vide pour ignorer): " url
      if [ -n "$url" ]; then
        git clone "$url" "$DIR/ProjetBibliotheque"
      else
        echo "Clonage backend ignoré"
      fi
    fi
  fi
fi

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

if docker compose version >/dev/null 2>&1; then
  COMPOSE_CMD="docker compose"
else
  COMPOSE_CMD="docker-compose"
fi

# Cherche docker-compose.yml local ou dans ProjetBibliotheque
COMPOSE_FILE="$DIR/docker-compose.yml"
if [ ! -f "$COMPOSE_FILE" ]; then
  if [ -f "$DIR/ProjetBibliotheque/docker-compose.yml" ]; then
    COMPOSE_FILE="$DIR/ProjetBibliotheque/docker-compose.yml"
  else
    echo "Aucun docker-compose.yml trouvé dans le dossier racine ni dans ProjetBibliotheque." >&2
    exit 1
  fi
fi

$COMPOSE_CMD -f "$COMPOSE_FILE" up --build -d

echo "Services demarres - Frontend: http://localhost  Backend: http://localhost:8080  PostgreSQL:5432"
