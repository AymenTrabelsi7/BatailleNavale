# Bataille Navale - Projet Programmation Réseau

#### Cette page fait office de description complète du projet. Elle doit permettre de comprendre tout du fonctionnement interne du projet.

## Sommaire :

  ### [1.Description Générale](#1--description-générale)
  
  ### [2.Présentation structurelle du programme](#2--présentation-structurelle-du-programme)
  
  ### [3.Présentation du protocole de communication](#3--présentation-du-protocole-de-communication)
  
  ### [4.Présentation algorithmique du programme](#4--présentation-algorithmique-du-programme)
  
  ### [5.Informations utiles](#5--informations-utiles)

##  
  
  ### 1.  Description Générale
  
  #### Fonctionnement
  
  Le jeu est architecturé sur le modèle Client-Serveur. Chaque joueur constitue un client, et éxécute sa propre instance du jeu. Le serveur, lui, sert d'orchestre de   communication entre les deux joueurs : il prévient les joueurs quand la partie peut commencer, il leur envoie les attaques de leur adversaire pour qu'il les traite, mais il est aussi chargé de décider du gagnant (il est cependant prévu de déplaçer le calcul des résultats des attaques sur le serveur, ce qui serait plus logique). Chaque joueur Lorsque le client est lancé, le joueur rentre son nom d'utilisateur et se connecte au salon. Il attend ensuite qu'un autre joueur se connecte. Lorsqu'il y a deux joueurs, la partie commence par le placement des bateaux : chaque joueur place ses bateaux de leur côté, puis attend que l'autre joueur ait fini également. Pour placer un bateau il faut deux coordonnées (début et fin du bateau). Le placement du bateau doit respecter certaines conditions :
  * Le bateau doit être complètement horizontal ou complètement vertical (pas de bateaux en diagonale). Autrement dit, le bateau doit avoir un axe qui ne varie pas.
  * Le bateau doit faire la longueur de son type (par exemple si c'est un croiseur, il doit faire exactement 2 cases).
  * Le bateau doit (évidemment) être placé sur des coordonnées qui ne sont pas déjà occupées par un autre bateau
  * Le bateau ne doit pas être placé sur des cases adjaçentes à un autre: c'est-à-dire que pour toutes les cases du bateau, il ne doit pas y a voir d'autre bateau en haut, en bas, à gauche et à droite de la case (par contre en haut à droite par exemple c'est autorisé).

A chaque placement de bateau effectué, le tableau du joueur est affiché, avec le bateau nouvellement ajouté. Le tableau ressemble à ceci :

![Tableau du joueur](/doc_ressources/tableau_bateaux.png)

Ensuite le jeu peut commencer : tour à tour, les joueurs entre des coordonnées pour les attaquer. Avant de choisir, il peut regarder son tableau d'attaques, c'est-à-dire les cases qu'il a déjà attaquées : 

![Tableau des attaques](/doc_ressources/tableau_attaques.png)

Après avoir envoyé l'attaque, le joueur reçoit ensuite le résultat, et attend son prochain tour.

La partie lorsqu'il y a un perdant, déterminé par le serveur. Le programme propose ensuite aux deux joueurs de recommencer une partie. Si les deux joueurs acceptent, la partie se réinitialise et une nouvelle partie est lançée. Sinon, les clients se déconnectent du salon.
  
  #### Note
  
  L'écriture des coordonnées prend la forme "{Lettre}{Chiffre}" Avec Lettre = A-J et Chiffre = 1-10. Par exemple : "A2" est une coordonnée correcte, comme "J10" ou "F9". Mais "A0" ou "a1", "A 1", "K1" ou encore "G11" ne sont pas correctes.
  
  ### 2.  Présentation structurelle du programme
  
  L'arborescence du dossier source est structurée comme suit :
  
  ![Arborescence du src/](/doc_ressources/arborescence_src.png)
  
  Les packages Client/Serveur contiennent les points d'entrée des deux programmes. Il gèrent le déroulement de la partie, la communication et les entrées utilisateur    pour le package Client. Le package BatailleNavale contient les classes nécessaires au jeu lui-même. Il permet de vérifier les règles de placement des bateaux, créer et afficher les tableaux, ajouter des bateaux au tableau, vérifier une attaque... Ce package est utilisé uniquement par le package Client. Le package Common contient l'interface de communication permettant d'appeler les différentes méthodes de gestion des requêtes. La partie communication est expliquée dans [la partie suivante](#3--présentation-du-protocole-de-communication).

  Si on veut représenter la hiérarchie des instances des classes, c'est-à-dire les liens d'instanciation entre les classes, on peut le représenter de cette manière :
  
  ![Hierarchies des instances de classes](/doc_ressources/hierarchies.bmp)

  Les flèches indiquent les communications effectuées via un Socket
  
  ### 3.  Présentation du protocole de communication
  
  La communication entre les clients et le serveur est basée sur un protocole. Ce protocole est lui-même basé sur un certain nombre de "requêtes", ou "commandes", qui représentent chacune une information et donc une action à effectuer.
  
  ![Zoom sur les communications](/doc_ressources/communication_zoom.png)
  
  Ces requêtes sont identifiées par un caractère, et ont chacune leur propre syntaxe. Elles sont séparées en 2 catégories : Émission et Réception. Les requêtes d'émission sont des requêtes qui gèrent l'envoi d'informations, et les requêtes de réception gèrent les informations entrantes, et les actions à effectuer.
  Les clients et le serveur ont le même nombre de requêtes : chaque commande d'émission du client a son équivalent réception chez le serveur, et inversement.
  Ces requêtes transmettent toutes les informations qui ont besoins d'être transmises : si les joueurs sont bien connectés, si ils ont fini de placer leurs bateaux, si l'attaque a été réussie, si le joueur a gagné, etc... On peut représenter ces commandes sous forme d'un tableau, comme ceci :
  
  #### Client
  
  
  **Type**|**Identifiant**|**Syntaxe**|**Description**
:-----:|:-----:|:-----:|:-----:
Réception|/c|c/`true-false`|Indique aux deux joueurs qu’ils sont deux dans le salon et que la partie peut commencer.
Réception|/b|b/`true-false` |Indique que aux deux joueurs qu’ils ont fini de placer leurs bateaux.
Réception|/t|t/`true-false`|Indique à un joueur si c’est son tour (true) ou non (false).
Réception|/i|a/i/`Coord`|Indique au joueur qu’il reçoit une attaque aux coordonnées spécifiées. Le joueur doit traiter l’attaque et renvoyer le résultat avec la commande d’émission /o.
Réception|/o|a/o/’null’ ou a/o/`Bateau` ou a/o/`Bateau`/’coulé’|Indique au joueur les résultats de l’attaque qu’il a lui-même engagé précédemment.
Réception|/g|g/`true-false`|Indique au joueur s’il a gagné ou perdu.
Réception|/r|r/`true-false`|Indique au joueur si la partie va recommencer (renvoie true si les deux joueurs ont accepté de rejouer, false sinon).
Émission|/b|b/`Bateau`/`Coord` ou b/`true-false` |Dans le 1er cas, envoie au serveur le bateau que l’on vient de placer, avec ses coordonnées. Dans le 2e cas, indique au serveur que le joueur a fini de placer ses bateaux.
Émission|/i|a/i/`Coord`|Envoie une attaque au serveur, pour qu’il la transfère à l’adversaire afin de recevoir le résultat.
Émission|/o|a/o/’null’ ou a/o/`Bateau` ou a/o/`Bateau`/’coulé’|Envoie les résultats de l’attaque subie. Dans le 1er cas, l’attaque est ratée, dans le 2e un bateau est touché, et dans le 3e il est coulé.
Émission|/u|u/`username`|Envoie l’username choisi au serveur.
Émission|/r|r/`true-false`|Indique au serveur si le joueur veut recommencer.
  
  
  #### Serveur
  
  Pour le serveur, c'est exactement pareil, sauf que les types (Émissions/Réceptions) sont inversés : 
  
  **Type**|**Identifiant**|**Syntaxe**|**Description**
:-----:|:-----:|:-----:|:-----:
Émission|/c|c/`true-false`|Indique aux deux joueurs qu’ils sont deux dans le salon et que la partie peut commencer.
Émission|/b|b/`true-false` |Indique que aux deux joueurs qu’ils ont fini de placer leurs bateaux.
Émission|/t|t/`true-false`|Indique à un joueur si c’est son tour (true) ou non (false).
Émission|/i|a/i/`Coord`|Indique au joueur qu’il reçoit une attaque aux coordonnées spécifiées. Le joueur doit traiter l’attaque et renvoyer le résultat avec la commande d’émission /o.
Émission|/o|a/o/’null’ ou a/o/`Bateau` ou a/o/`Bateau`/’coulé’|Indique au joueur les résultats de l’attaque qu’il a lui-même engagé précédemment.
Émission|/g|g/`true-false`|Indique au joueur s’il a gagné ou perdu.
Émission|/r|r/`true-false`|Indique au joueur si la partie va recommencer (renvoie true si les deux joueurs ont accepté de rejouer, false sinon).
Réception|/b|b/`Bateau`/`Coord` ou b/`true-false` |Dans le 1er cas, envoie au serveur le bateau que l’on vient de placer, avec ses coordonnées. Dans le 2e cas, indique au serveur que le joueur a fini de placer ses bateaux.
Réception|/i|a/i/`Coord`|Envoie une attaque au serveur, pour qu’il la transfère à l’adversaire afin de recevoir le résultat.
Réception|/o|a/o/’null’ ou a/o/`Bateau` ou a/o/`Bateau`/’coulé’|Envoie les résultats de l’attaque subie. Dans le 1er cas, l’attaque est ratée, dans le 2e un bateau est touché, et dans le 3e il est coulé.
Réception|/u|u/`username`|Envoie l’username choisi au serveur.
Réception|/r|r/`true-false`|Indique au serveur si le joueur veut recommencer.

Noter que même si  les même commandes sont présentes dans les deux tableaux, le processus éxécuté est complètement différent : par exemple, si le client reçoit la commande "a/i/A1", cela signifie qu'il est attaqué sur A1 et qu'il doit vérifier sa case puis renvoyer le résultat via la commande d'émission "a/o/". Mais si le serveur reçoit "a/i/A1", cela signifie que le joueur qui a envoyé cette requête souhaite attaquer l'adversaire sur cette case, le serveur doit donc transférer le message à l'adversaire, attendre sa réponse et l'envoyer au joueur attaquant. Cependant elles portent le même nom car elles éxécutent la même tâche : traiter une attaque envoyée par un joueur.

Dans le programme, ces requêtes sont représentées par des interfaces de type `RequeteIntf` définie ici :
```java
package Common;

public interface RequeteIntf {
	public void handleRequest(String request);
}
```
Chaque requête est définie par un identifiant et une implémentation de l'interface `RequeteIntf`. On peut donc regrouper ces requêtes dans des tables de hashage, comme ceci :
```java
private HashMap<Character,RequeteIntf> handleReceive;
private HashMap<Character,RequeteIntf> handleSend;
```
Si on veut ajouter une requête dans cette table, on peut le faire de cette manière (ici l'ajout de la requête "/c") :
```java
handleReceive.put('c', new RequeteIntf() {
  public void handleRequest(String request) {
    String parsed = request.split("/")[1];
    if(parsed.equals("true")) {
      joueursConnectes = true;
    }
    else if(parsed.equals("false")) joueursConnectes = false;
  }
});
```

Le Listener se charge d'écouter le socket et d'appeller les callbacks des récepteurs si il reçoit l'identifiant en question. Le Main se charge d'appeller les callbacks émetteurs pour notifier le destinataire d'une action utilisateur.
  
  ### 4.  Présentation algorithmique du programme
  
  ### 5.  Informations utiles

Liste d'inputs à copier coller sur les deux clients pour faire réaliser une séquence complète d'une partie automatiquement :

A1

A2

C1

C3

E1

E4

G1

G4

I1

I5

A1

A2

C1

C2

C3

E1

E2

E3

E4

G1

G2

G3

G4

I1

I2

I3

I4

I5

## [Haut de page](#bataille-navale---projet-programmation-réseau)


