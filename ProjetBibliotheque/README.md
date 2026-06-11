Visualisation des diagrammes PlantUML (.puml) dans IntelliJ

Ce projet contient des diagrammes UML au format .puml (PlantUML).
Pour pouvoir les visualiser correctement, il est nécessaire d’installer le plugin PlantUML Integration dans IntelliJ.

Les diagrammes sont fournis en version source (.puml) et en version image (.png) pour une lecture sans configuration.

Structure des diagrammes

Fichiers source PlantUML :

/UML


Images générées :

/UML/image
1. Installation du plugin PlantUML
   Étapes
   Ouvrir IntelliJ IDEA
   Aller dans :
   File → Settings (ou Ctrl + Alt + S)
   Dans le menu de gauche, cliquer sur :
   Plugins
   Dans l’onglet Marketplace, rechercher :
   PlantUML
   Cliquer sur Install
   Redémarrer IntelliJ après l’installation
2. Configuration dans IntelliJ
   Aller dans :
   File → Settings → Other Settings → PlantUML
   Vérifier que l’option suivante est activée :
   Enable PlantUML 
3. Visualisation des diagrammes
   Ouvrir un fichier .puml
   Faire un clic droit :
   Show Diagram
4. Problèmes courants
   Le diagramme ne s’affiche pas
   Vérifier que Java est installé
   Vérifier que le plugin est bien activé
5. Alternative

Si la visualisation via IntelliJ ne fonctionne pas, les diagrammes sont disponibles en version image dans le dossier :

/UML/image

Ces images ont été générées avec la commande suivante :

java -jar plantuml.jar fichier.puml

