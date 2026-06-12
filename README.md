Projet Bibliothèque — coordination

Résumé
-------
Ce dépôt fournit des scripts et des workflows pour démarrer et tester localement
le projet « Projet Bibliothèque » (frontend Angular + backend Spring Boot).

Prérequis
---------
- Docker (ou Docker Desktop)
- Pour Windows : PowerShell ou CMD

Démarrage rapide (sans cloner le dépôt)
-------------------------------------
1. Créez un dossier vide et placez-vous dedans :

```bash
mkdir projet-bibliotheque && cd projet-bibliotheque
```

2. Téléchargez le script adapté à votre système et exécutez-le.

PowerShell (Windows) :
```powershell
# Télécharger start.ps1 depuis le dépôt et lancer
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/cs2i-kevin-boedec/ProjetBibliCICD/main/start.ps1" -OutFile .\start.ps1
.\start.ps1
```

Invite de commandes (CMD) :
```cmd
:: Télécharger start.bat puis lancer
curl -L -o start.bat https://raw.githubusercontent.com/cs2i-kevin-boedec/ProjetBibliCICD/main/start.bat
start.bat
```

Bash / WSL / macOS :
```bash
curl -L -o start.sh https://raw.githubusercontent.com/cs2i-kevin-boedec/ProjetBibliCICD/main/start.sh
chmod +x start.sh
./start.sh
```

Les scripts téléchargés automatisent le téléchargement/installation des composants
nécessaires et lancent Docker Compose en utilisant le fichier
`ProjetBibliotheque/docker-compose.yml`. Vous n'avez pas besoin de cloner
ce dépôt au préalable.

Comportement des scripts
------------------------
- Les scripts orchestrent Docker Compose via `ProjetBibliotheque/docker-compose.yml`.
- Ils gèrent automatiquement le téléchargement des sous-projets ou images
  nécessaires ; si `git` est disponible, il peut être utilisé pour cloner des
  sous-dépôts. Le reste du processus est automatisé.
- Pour arrêter proprement les services, utilisez les scripts fournis :

  PowerShell :
  ```powershell
  .\stop.ps1
  ```

  Bash :
  ```bash
  ./stop.sh
  ```

  CMD :
  ```cmd
  stop.bat
  ```

Workflows GitHub Actions
-------------------------
Les workflows se trouvent dans `.github/workflows` et couvrent le frontend
et le backend :

- `frontend-ci.yml` : installe les dépendances, et exécute les jobs séparés
  `setup` (install), `build`, `lint`, `test`. Objectif : valider le build
  Angular, la qualité du code et les tests unitaires.
- `backend-ci.yml` : configure JDK, puis exécute les jobs `setup`, `build`
  (packaging) et `test`. Objectif : compiler et tester le module Spring Boot.

Ces workflows sont déclenchés sur `push` et `pull_request` vers `main`.
Chaque job s'exécute sur un runner isolé ; les étapes d'installation (npm ci / mvn)
sont effectuées dans les jobs avant le build/test.

API documentation (Swagger)
---------------------------
Actuellement, la génération d'API documentation (Swagger/OpenAPI) est documenté a cette adresse:
http://localhost:8080/swagger-ui/index.html

Voir et utiliser le projet
-------------------------
Après démarrage via `start.*`, les composants sont accessibles localement :

- Frontend (interface web) : http://localhost:4200
- Backend (API) : http://localhost:8080
- Base de données PostgreSQL : port 5432 (consultez `ProjetBibliotheque/docker-compose.yml` pour les identifiants)

Consulter les logs
------------------
- Journaux de tous les services :
```bash
docker compose -f ProjetBibliotheque/docker-compose.yml logs -f
```
- Journaux d'un service précis :
```bash
docker compose -f ProjetBibliotheque/docker-compose.yml logs -f <service_name>
```

Exécuter uniquement un composant en local
---------------------------------------
- Frontend en mode développement :
```bash
cd front-bibliotheque
npm install
npm start    # serveur dev Angular (http://localhost:4200)
```
- Backend en mode développement :
```bash
cd ProjetBibliotheque/bibliotheque
./mvnw spring-boot:run    # serveur dev (http://localhost:8080)
```

Tests
-----
- Frontend : `cd front-bibliotheque && npm test`
- Backend : `cd ProjetBibliotheque/bibliotheque && ./mvnw test`

ajout possible : 
GSCR -> github pousser les images docker, pas besoin de build local
ajouter makerfile
dependancy-check (OWASP)