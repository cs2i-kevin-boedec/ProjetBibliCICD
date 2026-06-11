@echo off
REM stop.bat - arrête les services Docker (Windows CMD)
pushd "%~dp0"
REM Utiliser uniquement le docker-compose.yml dans ProjetBibliotheque
set COMPOSE_FILE=%~dp0\ProjetBibliotheque\docker-compose.yml
if not exist "%COMPOSE_FILE%" (
  echo docker-compose.yml introuvable dans ProjetBibliotheque.
  popd
  exit /b 1
)

where docker >nul 2>nul || (
  echo Docker non trouvé. Impossible d'arreter les services.
  popd
  exit /b 1
)

docker compose -f "%COMPOSE_FILE%" down --volumes --remove-orphans || docker-compose -f "%COMPOSE_FILE%" down --volumes --remove-orphans
popd
echo Services arretés
