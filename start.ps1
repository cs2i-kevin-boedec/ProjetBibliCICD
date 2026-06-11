#!/usr/bin/env pwsh
<#
start.ps1 - Clone le projet si necessaire puis lance Docker
#>

if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
    Write-Error "Docker est introuvable dans le PATH."
    exit 1
}

if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Error "Git est introuvable dans le PATH."
    exit 1
}

$repoUrl = "https://github.com/cs2i-kevin-boedec/ProjetBibliCICD.git"
$repoFolder = "ProjetBibliCICD"
$repoPath = Join-Path $PSScriptRoot $repoFolder

if (-not (Test-Path $repoPath)) {
    Write-Host "Clonage du depot..."
    git clone $repoUrl $repoPath

    if ($LASTEXITCODE -ne 0) {
        Write-Error "Impossible de cloner le depot."
        exit 1
    }
}
else {
    Write-Host "Depot deja present."
}

Push-Location $repoPath

try {
    # Utiliser uniquement le docker-compose.yml dans ProjetBibliotheque
    $composeFile = Join-Path $repoPath "ProjetBibliotheque\docker-compose.yml"
    if (-not (Test-Path $composeFile)) {
        Write-Error "docker-compose.yml introuvable dans $repoPath\ProjetBibliotheque"
        exit 1
    }
    Write-Host "Utilisation du fichier compose : $composeFile"

    $useDockerComposeV2 = $false

    try {
        & docker compose version *> $null
        if ($LASTEXITCODE -eq 0) {
            $useDockerComposeV2 = $true
        }
    }
    catch {
        $useDockerComposeV2 = $false
    }

    if ($useDockerComposeV2) {
        Write-Host "Demarrage avec docker compose..."
        docker compose -f $composeFile up --build -d
    }
    else {
        Write-Host "Demarrage avec docker-compose..."
        docker-compose -f $composeFile up --build -d
    }

    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "Services demarres."
        Write-Host "Frontend : http://localhost"
        Write-Host "Backend  : http://localhost:8080"
    }
    else {
        Write-Error "Erreur lors du lancement des conteneurs."
    }
}
finally {
    Pop-Location
}