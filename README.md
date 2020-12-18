# Bataille Navale - Projet Programmation Réseau

Cette page fait office de description complète du projet

Sommaire :

  ### [1.Description Générale](#1--description-générale)
  
  ### [2.Présentation structurelle du programme](#2--présentation-structurelle-du-programme)
  
  ### [3.Présentation du protocole de communication](#3--présentation-du-protocole-de-communication)
  
  ### [4.Présentation algorithmique du programme](#4--présentation-algorithmique-du-programme)
  
  ### [5.Informations utiles](#5--informations-utiles)
  
  
  
  ### 1.  Description Générale
  
  ### 2.  Présentation structurelle du programme
  
  ![Hierarchies des instances de classes](/doc_ressources/hierarchies.bmp)
  
  ### 3.  Présentation du protocole de communication
  
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
  
  
  
  
  
  ### 4.  Présentation algorithmique du programme
  
  ### 5.  Informations utiles

