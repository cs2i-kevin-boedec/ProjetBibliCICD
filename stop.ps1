#!/usr/bin/env pwsh
<# stop.ps1 - Arrête les services Docker du projet proprement #>
Push-Location $PSScriptRoot
try {
  $composeFile = Join-Path $PSScriptRoot 'docker-compose.yml'
  if (-not (Test-Path $composeFile)) {
    $alt = Join-Path $PSScriptRoot 'ProjetBibliotheque\docker-compose.yml'
    if (Test-Path $alt) { $composeFile = $alt } else {
      Write-Error "Aucun fichier docker-compose.yml trouvé dans le dossier racine ni dans ProjetBibliotheque."
      exit 1
    }
  }

  if (Get-Command docker -ErrorAction SilentlyContinue) {
    Write-Host "Arrêt des services via docker compose -f $composeFile down"
    docker compose -f $composeFile down --volumes --remove-orphans 2>&1 | Write-Host
  } else {
    Write-Error "Docker introuvable. Impossible d'arreter les services."
    exit 1
  }
} finally {
  Pop-Location
}
