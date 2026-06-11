#  Bibliothèque Universitaire – Backend

##  Description

Ce projet correspond au backend d’une application de gestion de bibliothèque universitaire.  
Il permet de gérer les utilisateurs, les ouvrages, les emprunts et les retours, en respectant des règles métier spécifiques (caution, retards, disponibilité).

L’application est développée avec **Spring Boot** et expose une **API REST** consommée par un frontend Angular.

---

##  Technologies utilisées

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- H2 Database (in-memory)
- Maven
- JUnit 5 / Mockito (tests)

---

##  Architecture

Le projet suit une architecture en couches :

- **Controller** : gestion des requêtes HTTP (API REST)
- **Service** : logique métier
- **Repository** : accès aux données (JPA)
- **Entity** : modèles persistants
- **DTO** : objets de transfert
- **Mapper** : conversion Entity ↔ DTO
- **Exception** : gestion centralisée des erreurs
- **Observer** : gestion des notifications (retards)
- **Factory** : création des objets métier

---

##  Fonctionnalités principales

L’application permet de gérer :

- les utilisateurs (étudiant, enseignant, particulier)
- les ouvrages (livres, revues)
- les emprunts / retours
- les retards
- les emplacements de stockage

 Une fonctionnalité de réservation est présente dans le backend mais n’est pas exposée dans l’interface utilisateur finale.

---

##  Règles métier importantes

- Durée d’emprunt : **15 jours**
- Interdiction d’emprunt en cas de retard
- Un seul exemplaire d’une même ressource par utilisateur
- Vérification de la caution disponible
- Gestion de la disponibilité des exemplaires

---

##  Lancement du projet

### Prérequis

- Java **17**
- Maven
- IntelliJ IDEA (recommandé)

---

### Démarrage

Depuis IntelliJ :
- Ouvrir la classe :
  BibliothequeApplication.java
- - Cliquer sur **Run**

Ou en terminal :

mvn spring-boot:run


L’API démarre sur :

http://localhost:8080


---

##  Base de données

Le projet utilise une base **H2 en mémoire**.

- Les données sont **réinitialisées à chaque démarrage**
- Un jeu de données est automatiquement injecté

Console H2 :

http://localhost:8080/h2-console


Paramètres :

JDBC URL : jdbc:h2:mem:bibliotheque
User : sa
Password : (vide)


---

##  Jeu de données automatique

Un jeu de données de démonstration est chargé au démarrage via :


DataInitializer (CommandLineRunner)


Cela permet de tester rapidement :
- la connexion
- les emprunts
- les retours
- les retards

---

##  Comptes de test

### Bibliothécaire

login : biblio
motDePasse : biblio123


### Étudiant

login : alice
motDePasse : alice123


### Enseignant

login : pmartin
motDePasse : paul123


---

##  Tables principales

- UTILISATEUR
- ETUDIANT
- ENSEIGNANT
- PARTICULIER
- COMPTE
- OUVRAGE
- LIVRE
- REVUE
- EXEMPLAIRE
- EMPRUNT
- EMPLACEMENT_STOCKAGE

---

##  Tests

Des tests unitaires ont été réalisés avec **JUnit 5** et **Mockito** sur :

- Authentification
- Emprunts
- Retours
- Retards
- Factory Pattern
- Observer Pattern

---

## 🔌 Tests API

Un fichier :

requests.http

permet de tester rapidement les endpoints.

### Ordre conseillé :
1. Lister les ouvrages
2. Emprunter un ouvrage
3. Consulter les emprunts
4. Tester les retards
5. Retourner un ouvrage

---

##  Design Patterns utilisés

### Factory Method
Utilisé pour :
- la création des utilisateurs
- la création des livres et revues

Permet de respecter le principe d’ouverture/fermeture.

---

### Observer
Utilisé pour :
- la gestion des retards
- la notification des utilisateurs

Permet de découpler la logique métier de la gestion des notifications.

---

##  Ressources UML

Les diagrammes UML sont disponibles dans :


/UML


Contenu :
- Diagrammes de séquence (nominaux + erreurs)
- Diagramme de classes
- Versions `.puml` et `.png`

---

##  Remarques importantes

- Les réponses REST utilisent des **DTO** pour éviter les boucles JSON
- Les règles métier sont implémentées dans les **services**
- L’authentification est simplifiée dans le cadre du projet
- La base H2 est utilisée uniquement pour le développement et les tests

---

##  Évolutions possibles

- Mise en place d’une authentification sécurisée (JWT)
- Finalisation de la gestion des réservations
- Ajout de tests d’intégration
- Utilisation d’une base persistante (MySQL / PostgreSQL)

---