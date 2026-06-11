##  Lancement du projet

### 1. Prérequis

* Java **17**
* Maven
* IntelliJ IDEA Ultimate recommandé

---

### 2. Lancer l’application

Depuis IntelliJ :

* Ouvrir la classe :

```
BibliothequeApplication.java
```

* Cliquer sur **Run**

Ou en ligne de commande :

```bash
mvn spring-boot:run
```

L’application démarre sur :

```
http://localhost:8080
```

---

##  Jeu de données – Tests API

Un fichier de requêtes HTTP est disponible :

```
requests.http
```

Il permet de tester rapidement :

* création utilisateur
* création livre / revue
* recherche ouvrage
* emprunt
* retour
* retards
* réservation

###  Ordre conseillé

1. Créer un utilisateur
2. Créer un ouvrage
3. Lister les ouvrages
4. Effectuer un emprunt
5. Voir les emprunts utilisateur

---

## 🗄️ Accès base H2

Console disponible à :

```
http://localhost:8080/h2-console
```

### Paramètres de connexion

* JDBC URL :

```
jdbc:h2:mem:bibliotheque
```

* User :

```
sa
```

* Password :

```
(vide)
```

---

##  Tables importantes

* UTILISATEUR
* ETUDIANT
* ENSEIGNANT
* PARTICULIER
* COMPTE
* OUVRAGE
* LIVRE
* REVUE
* EXEMPLAIRE
* EMPRUNT
* RESERVATION
* EMPLACEMENT_STOCKAGE

---

##  Architecture technique

* Backend : **Spring Boot REST**
* ORM : **JPA / Hibernate**
* Base : **H2 in-memory**
* Validation : **Spring Validation**
* Mapping JSON : **DTO + Mapper**
* Design Patterns :

  * Factory Method (création ouvrages / utilisateurs)
  * Observer (gestion retards)

---

##  UML

Les diagrammes UML sont fournis :

* Diagramme de classes
* Diagrammes de séquence
* Use case

Certains diagrammes peuvent être générés automatiquement via IntelliJ :

```
clic droit package → Diagrams → Show Diagram
```

---

## ⚠️ Important

* La base H2 est **volatile** → les données sont perdues au redémarrage.
* L’authentification est simplifiée pour les besoins du TP.
* Les réponses REST utilisent des **DTO** pour éviter les boucles JSON.

