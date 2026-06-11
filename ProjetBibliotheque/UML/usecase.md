flowchart LR
    U[Utilisateur]
    B[Bibliothécaire]

    subgraph SYS[Système bibliothèque]
        UC1((Créer compte))
        UC2((Se connecter))
        UC3((Première connexion / changer mot de passe))
        UC4((Rechercher ouvrage))
        UC5((Emprunter ouvrage))
        UC6((Consulter liste des emprunts))
        UC7((Réserver ouvrage))
        UC8((Retourner ouvrage))
        UC9((Consulter emprunts en retard))
        UC10((Envoyer mails de relance))
        UC11((Ajouter ouvrage))
        UC12((Associer emplacement))
    end

    U --- UC1
    U --- UC2
    U --- UC4
    U --- UC5
    U --- UC6
    U --- UC7
    U --- UC8
    U --- UC3

    B --- UC2
    B --- UC8
    B --- UC9
    B --- UC10
    B --- UC11

    UC5 -. include .-> UC2
    UC5 -. include .-> UC4
    UC6 -. include .-> UC2
    UC7 -. include .-> UC2
    UC8 -. include .-> UC2
    UC9 -. include .-> UC2
    UC10 -. include .-> UC9
    UC11 -. include .-> UC2
    UC11 -. include .-> UC12
    UC3 -. extend .-> UC2