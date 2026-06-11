@echo off
REM start.bat - démarre les services Docker (Windows CMD)
where docker >nul 2>nul || (
  echo Docker non trouvé. Installez Docker Desktop.
  exit /b 1
)

pushd "%~dp0"
set COMPOSE_FILE=%~dp0docker-compose.yml
if not exist "%COMPOSE_FILE%" (
  if exist "%~dp0\ProjetBibliotheque\docker-compose.yml" (
    set COMPOSE_FILE=%~dp0\ProjetBibliotheque\docker-compose.yml
  ) else (
    echo Aucun docker-compose.yml trouvé dans le dossier racine ni dans ProjetBibliotheque.
    popd
    exit /b 1
  )
)
REM Clonage automatique si git est disponible et si les dossiers manquent
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

docker compose -f "%COMPOSE_FILE%" up --build -d || docker-compose -f "%COMPOSE_FILE%" up --build -d
popd
echo Services demarres - Frontend: http://localhost  Backend: http://localhost:8080  PostgreSQL:5432
