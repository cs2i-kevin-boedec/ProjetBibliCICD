#!/usr/bin/env bash
set -euo pipefail

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COMPOSE_FILE="$DIR/docker-compose.yml"
if [ ! -f "$COMPOSE_FILE" ]; then
  if [ -f "$DIR/ProjetBibliotheque/docker-compose.yml" ]; then
    COMPOSE_FILE="$DIR/ProjetBibliotheque/docker-compose.yml"
  else
    echo "Aucun docker-compose.yml trouvé dans le dossier racine ni dans ProjetBibliotheque." >&2
    exit 1
  fi
fi

if ! command -v docker >/dev/null 2>&1; then
  echo "Docker non trouvé. Impossible d'arreter les services." >&2
  exit 1
fi

if docker compose version >/dev/null 2>&1; then
  docker compose -f "$COMPOSE_FILE" down --volumes --remove-orphans
else
  docker-compose -f "$COMPOSE_FILE" down --volumes --remove-orphans
fi

echo "Services arrêtés"
