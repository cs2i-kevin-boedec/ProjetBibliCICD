#!/usr/bin/env pwsh
<#
start.ps1 - Démarre les services Docker du projet en une commande
#>
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
  Write-Error "Docker n'est pas installé ou introuvable dans PATH. Installez Docker Desktop et relancez."
  exit 1
}

# Vérifier et cloner les dépôts frontend/backend si nécessaire
function Clone-IfMissing($dirName, $envVarName, $promptText) {
  $target = Join-Path $PSScriptRoot $dirName
  if (Test-Path $target) { return }

  if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Error "Git introuvable. Installez Git pour permettre le clonage automatique, ou clonez manuellement les dépôts dans $dirName."
    return
  }

  $repoUrl = $env:$envVarName
  if ([string]::IsNullOrWhiteSpace($repoUrl)) {
    $repoUrl = Read-Host "$promptText (URL Git) — laisser vide pour ignorer"
  }
  if ([string]::IsNullOrWhiteSpace($repoUrl)) {
    Write-Host "Aucun URL fourni — saut du clonage pour $dirName"
    return
  }

  Write-Host "Clonage de $repoUrl dans $dirName..."
  git clone $repoUrl $target
}

Clone-IfMissing 'front-bibliotheque' 'FRONTEND_REPO' 'URL du dépôt frontend'
Clone-IfMissing 'ProjetBibliotheque' 'BACKEND_REPO' 'URL du dépôt backend'

Push-Location $PSScriptRoot
try {
  $composeCmd = "docker-compose"
  $composeAvailable = $false
  try {
    & docker compose version > $null 2>&1
    if ($LASTEXITCODE -eq 0) { $composeAvailable = $true }
  } catch {
    $composeAvailable = $false
  }
  if ($composeAvailable) { $composeCmd = "docker compose" }

  Write-Host ('Execution : {0} up --build -d' -f $composeCmd)
  # Cherche un fichier docker-compose.yml
  $composeFile = Join-Path $PSScriptRoot 'docker-compose.yml'
  if (-not (Test-Path $composeFile)) {
    $alt = Join-Path $PSScriptRoot 'ProjetBibliotheque\docker-compose.yml'
    if (Test-Path $alt) { $composeFile = $alt } else {
      Write-Error "Aucun fichier docker-compose.yml trouvé dans le dossier racine ni dans ProjetBibliotheque."
      exit 1
    }
  }

  if ($composeCmd -eq "docker compose") {
    & docker compose -f $composeFile up --build -d
  } else {
    & docker-compose -f $composeFile up --build -d
  }

  Write-Host 'Services demarres - Frontend: http://localhost  Backend: http://localhost:8080  PostgreSQL:5432'
} finally {
  Pop-Location
}
