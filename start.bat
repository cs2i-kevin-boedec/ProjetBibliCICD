@echo off
REM start.bat - démarre les services Docker (Windows CMD)
where docker >nul 2>nul || (
  echo Docker non trouvé. Installez Docker Desktop.
  exit /b 1
)

pushd "%~dp0"
REM Clonage automatique des dépôts si manquants
where git >nul 2>nul
if %ERRORLEVEL%==0 (
  if not exist "%~dp0front-bibliotheque" (
    if defined FRONTEND_REPO (
      git clone %FRONTEND_REPO% "%~dp0front-bibliotheque"
    ) else (
      set /p FEURL=URL du dépôt frontend (laisser vide pour ignorer):
      if not "%FEURL%"=="" git clone %FEURL% "%~dp0front-bibliotheque"
    )
  )
  if not exist "%~dp0ProjetBibliotheque" (
    if defined BACKEND_REPO (
      git clone %BACKEND_REPO% "%~dp0ProjetBibliotheque"
    ) else (
      set /p BEURL=URL du dépôt backend (laisser vide pour ignorer):
      if not "%BEURL%"=="" git clone %BEURL% "%~dp0ProjetBibliotheque"
    )
  )
)

REM Utiliser uniquement docker-compose dans ProjetBibliotheque
set COMPOSE_FILE=%~dp0\ProjetBibliotheque\docker-compose.yml
if not exist "%COMPOSE_FILE%" (
  echo docker-compose.yml introuvable dans ProjetBibliotheque.
  popd
  exit /b 1
)

docker compose -f "%COMPOSE_FILE%" up --build -d || docker-compose -f "%COMPOSE_FILE%" up --build -d
popd
echo Services demarres - Frontend: http://localhost  Backend: http://localhost:8080  PostgreSQL:5432
