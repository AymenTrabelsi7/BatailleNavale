# Bataille Navale - Projet Programmation Réseau

### Cette page fait office de description complète du projet. Cette documentation, couplée avec les commentaires du programme, doit permettre de comprendre tout du fonctionnement interne du projet.

## Sommaire :

  ### [1.Description Générale](#1--description-générale)
  
  ### [2.Présentation structurelle du programme](#2--présentation-structurelle-du-programme)
  
  ### [3.Présentation du protocole de communication](#3--présentation-du-protocole-de-communication)
  
  ### [4.Présentation algorithmique du programme](#4--présentation-algorithmique-du-programme)
  
  ### [5.Informations utiles](#5--informations-utiles)

##  
  
  ### 1.  Description Générale
  
  Le jeu est architecturé sur le modèle Client-Serveur. Chaque joueur constitue un client, et éxécute sa propre instance du jeu. Le serveur, lui, sert d'orchestre de   communication entre les deux joueurs : il prévient les joueurs quand la partie peut commencer, il leur envoie les attaques de leur adversaire pour qu'il les traite, mais il est aussi chargé de décider du gagnant (il est cependant prévu de déplaçer le calcul des résultats des attaques sur le serveur, ce qui serait plus logique).
  
  ### 2.  Présentation structurelle du programme
  
  ![Hierarchies des instances de classes](/doc_ressources/hierarchies.bmp)
  
  ### 3.  Présentation du protocole de communication
  
  ![Zoom sur les communications](/doc_ressources/communication_zoom.png)
  
  
  
  
  
  **Identifiant**|**Syntaxe**|**Description**
:-----:|:-----:|:-----:
/c|c/`true-false`|Indique aux deux joueurs qu’ils sont deux dans le salon et que la partie peut commencer.
/b|b/`true-false` |Indique que aux deux joueurs qu’ils ont fini de placer leurs bateaux.
/t|t/`true-false`|Indique à un joueur si c’est son tour (true) ou non (false).
/i|a/i/`Coord`|Indique au joueur qu’il reçoit une attaque aux coordonnées spécifiées. Le joueur doit traiter l’attaque et renvoyer le résultat avec la commande d’émission /o.
/o|a/o/’null’ ou a/o/`Bateau` ou a/o/`Bateau`/’coulé’|Indique au joueur les résultats de l’attaque qu’il a lui-même engagé précédemment.
/g|g/`true-false`|Indique au joueur s’il a gagné ou perdu.
/r|r/`true-false`|Indique au joueur si la partie va recommencer (renvoie true si les deux joueurs ont accepté de rejouer, false sinon).
/b|b/`Bateau`/`Coord` ou b/`true-false` |Dans le 1er cas, envoie au serveur le bateau que l’on vient de placer, avec ses coordonnées. Dans le 2e cas, indique au serveur que le joueur a fini de placer ses bateaux.
/i|a/i/`Coord`|Envoie une attaque au serveur, pour qu’il la transfère à l’adversaire afin de recevoir le résultat.
/o|a/o/’null’ ou a/o/`Bateau` ou a/o/`Bateau`/’coulé’|Envoie les résultats de l’attaque subie. Dans le 1er cas, l’attaque est ratée, dans le 2e un bateau est touché, et dans le 3e il est coulé.
/u|u/`username`|Envoie l’username choisi au serveur.
/r|r/`true-false`|Indique au serveur si le joueur veut recommencer.
  
  #### Client
  


  
  
  #### Serveur
  
  
  
  
  
  ### 4.  Présentation algorithmique du programme
  
  ### 5.  Informations utiles

