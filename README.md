<!--
V1 :<br>
test unitaire (mvn test)<br>
contact list<br>
gestion pseudo<br>
client qui broadcast son pseudo<br>

Les tâches pour la V1 :<br>
FAIT    mettre sous forme de packages le code <br>
A FAIRE rajouter un ID pour quand découverte envoie ID + pseudo<br>
FAIT    communiquer le pseudonyme choisi aux autres utilisateurs<br>
FAIT- udp marche moyennement maintenir une liste des utilisateurs connectés<br>
FAIT    avoir un main qui fait un focntionnement classique<br>
FAIT    (fonctionne aussi en ligne de commande)<br>

!!!! FAIRE DES TESTS<br>
FAIT ajouter commentaires (README) sur comment marche le code <br>
FAIT    le faire fonctionner (faut que ça marche sur maven)<br>


README :<br>
A FAIRE Quelques lignes décrivant la nature et les objectifs du projet<br>
FAIT Instructions sur la façon de compiler et d'exécuter le programme à partir du code source<br>

VERIFIER GIT:
FAIT Aucun artefact de compilation dans le dépôt (par exemple, fichiers .class).<br>
FAIT Aucun fichier de configuration (.idea, .project) dans le dépôt.<br>
FAIT    Le .gitignore le fichier est complet (par exemple, il empêche l'ajout des fichiers mentionnés ci-dessus).<br>
FAIT Les commits sont de taille raisonnable avec des messages significatifs.<br>

CODE PROPRE :
Les erreurs sont traitées correctement: 
    La décision d'ignorer une erreur
    de simplement l'afficher
    d'arrêter le traitement et de retourner l'erreur est justifiée.

FAIT    Votre gestion de thread est correcte: pas d'impasse, protection contre l'accès simultané.

-->
## ChatSystem
Le système de messagerie décentralisé, nous en sommes à la prise de contact.<br>
Lors d'une nouvelle connexion, nous faisons un boardcast sur le réseau pour identifier les autres et s'identifier.<br>
Nous vérifions l'unicité des pseudos.<br>
Nous mettons aussi la liste de contacts en ligne à jour.<br>
<br>
## Commande à executer


Pour copier le repertoire il faudra copier le repertoire, soit avec <br>
`git clone https://github.com/insa-4ir-chatsystem/SC-ChatSytem-Bedour-Wang`<br>

soit avec ça si vous n'avez pas accés au précéommande à executerdent, mais c pareil<br>
`git clone https://github.com/clementbedour/chat_system`<br>


Pour lancer les tests, vous devais être à la racine du projet, puis executer la commande :<br>

Pour effectuer les tests, et vérifier que vous êtes au bon endroit, vous pouvais faire :
`mvn test`

Pour pouvoir executer le projet dans le fonctionnement classique, il faudra :<br><br>
Être à la racine du projet<br><br>
## Compiler avec Maven 
Il faut faire :
<br> `mvn compile`<br><br>

<!--Avoir compiler l'ensemble des fichiers présents grace à la commande<br><br>
`javac chatsystem/network/*.java chatsystem/contacts/*.java chatsystem/controller/*.java chatsystem/ui/*.java chatsystem/*.java `<br><br>
-->
Pour finir, lancer le programme avec votre nom en argument, ici ça sera Dupont :<br><br> `java -cp target/classes chatsystem.Main Dupont`<br><br>

Pendant la phase d'utilisation<br>

Il se peut que les paquets UDP envoyés pour le ping-pong se perdent, ce qui provoque une déconnexion. Dans ce cas-là, on peut rencontrer un problème de désynchronisation entre les utilisateurs.<br><br>
