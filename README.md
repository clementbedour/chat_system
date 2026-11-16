V1 :<br>
test unitaire (mvn test)<br>
contact list<br>
gestion pseudo<br>
client qui broadcast son pseudo<br>
ajouter target dans gitignore (pour l'enlever mvn clear ou clean)
<br>
<br>
pom.xml<br>
readme<br>
repertoire src		-> main ->test<br>
.gitignore<br>
github 				-><br>
<br>
Singleton<br>
<--
à faire :
mettre sous forme de packages le code 
rajouter un ID pour quand découverte envoie ID + pseudo
communiquer le pseudonyme choisi aux autres utilisateurs
maintenir une liste des utilisateurs connectés
avoir un main qui fait un focntionnement classique
(fonctionne aussi en ligne de commande)
!!!! FAIRE DES TESTS (pas sur)
ajouter commentaires (README) sur comment marche le code et le faire fonctionner (faut que ça marche sur maven)

README :
Quelques lignes décrivant la nature et les objectifs du projet
Instructions sur la façon de compiler et d'exécuter le programme à partir du code source

VERIFIER GIT:
Aucun artefact de compilation dans le dépôt (par exemple, fichiers .class).
Aucun fichier de configuration (.idea, .project) dans le dépôt.
Le .gitignore le fichier est complet (par exemple, il empêche l'ajout des fichiers mentionnés ci-dessus).
Les commits sont de taille raisonnable avec des messages significatifs.

CODE PROPRE :
es erreurs sont traitées correctement: La décision (1) d'ignorer une erreur, (2) de simplement l'afficher, ou (3) d'arrêter le traitement et de retourner l'erreur est justifiée.
Votre gestion de thread est correcte: pas d'impasse, protection contre l'accès simultané.-->