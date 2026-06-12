#!/usr/bin/env bash
set -euo pipefail

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Utiliser uniquement le docker-compose.yml dans ProjetBibliotheque
COMPOSE_FILE="$DIR/ProjetBibliotheque/docker-compose.yml"
if [ ! -f "$COMPOSE_FILE" ]; then
  echo "Aucun docker-compose.yml trouvé dans $DIR/ProjetBibliotheque." >&2
  exit 1
fi

if ! command -v docker >/dev/null 2>&1; then
  echo "Docker non trouvé. Impossible d'arreter les services." >&2
  exit 1
fi

# S'assurer que l'utilisateur courant a les droits sur le dossier du projet (silencieux si non applicable)
if command -v chmod >/dev/null 2>&1; then
  chmod -R u+rwX "$DIR/ProjetBibliotheque" 2>/dev/null || true
fi

if docker compose version >/dev/null 2>&1; then
  docker compose -f "$COMPOSE_FILE" down --volumes --remove-orphans
else
  docker-compose -f "$COMPOSE_FILE" down --volumes --remove-orphans
fi

echo "Services arrêtés"
