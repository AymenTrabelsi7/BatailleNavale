package BatailleNavale;

public class BatailleNavale {
	
	/*C'est dans cette classe que se trouve le processing de base concernant le jeu lui-même.
	 * On y trouve les méthodes vérifierBateau, ajouterBateau et attaque. Ces fonctions de base
	 * seront utilisées par le Serveur pour effectuer tous les calculs dont il a besoin pour
	 * décider de l'avançement de la partie.*/
	
	
	//C'est là que sont associés les tableaux des joueurs
	//Le serveur modélise les joueurs avec 2 choses : un flux d'I/O, et un Tableau.


	private String gagne = null;
	
	
	public String getGagne() {
		if(gagne != null) {			
			synchronized(gagne) {			
				return gagne;
			}
		}
		else return null;
	}



	public void setGagne(String gagne) {
		this.gagne = gagne;
	}
	
	public boolean verifierBateau(Tableau tab, int[] coord1, int[] coord2, String typeBateau) {
		
		/*Méthode un peu complexe qui permet de vérifier plusieurs choses lorsqu'un joueur propose des coordonnées
		 *pour un de ses bateaux :
		 *-Que le bateau s'étend sur un seul axe, c'est-à-dire qu'il est soit horizontal soit vertical, et pas les 2
		 *-Que la longueur des coordonnées correspond à la longueur du type de bateau qu'il est censé placer (Longueur = Nb de cases)
		 *-Qu'aucun bateau n'est pas déjà présent sur ces coordonnées
		 *-Qu'aucun bateau ne se trouve sur les cases adjaçentes (gauche,droite,haut,bas, pas les diagonales)
		 *Si et seulement si les inputs répondent à tous ces critères, alors la méthode renvoie true, et le
		 *serveur valide la demande en ajoutant le bateau à la liste.*/
		
		
		
		
		//La méthode commence par vérifier si tous les points du bateau sont alignés sur un seul axe
		if((coord1[0] != coord2[0]) && (coord1[1] != coord2[1])) {
			return false;
		}
		
		
		
		//Ensuite, elle vérifie que le bateau correspond bien à la longueur de son type
		//La méthode statique getLongueurBateau permet de connaître la longueur que doit avoir le bateau
		//La valeur absolue sert à faire abstraction du sens du bateau
		//Le max est utilisé car à ce stade l'orientation du bateau n'est pas connue
		if(Bateau.getLongueurBateau(typeBateau) == Math.max(Math.abs(coord1[0]-coord2[0])+1, Math.abs(coord1[1]-coord2[1])+1)) {
			
			
			
			int debutBateau; //Sera la plus petite coordonnée des deux points (sur l'axe sur lequel le bateau s'étend),
							 //car ce sera plus simple d'aller du min vers le max dans la suite
			int longueurBateau;
			int currentBateau;//Contiendra la case du bateau qui sera en cours de vérification, et sera incrémentée au fur
							  //et à mesure
			int currentAdjacente; //Servira à vérifier les cases adjacentes sur l'axe variable
			int axeFixe; //Axe sur lequel le bateau ne varie pas
			int currentAxefixeAdjacente; //Servira à vérifier les cases adjacentes sur l'axe fixe
			int[][] tableau = tab.getTab();
			int BordSuperieurTableau = tableau.length - 1; //Dernière position du tableau, servira à éviter les
														   //IndexOutOfBoundException
			
			
			
			/*Cette partie du programme permet de déterminer l'orientation du bateau
			 *Cela permettra de savoir quel est l'axe fixe, et l'axe variable. Ce qui
			 *permettra de savoir sur quel axe effectuer l'incrémentation.*/
			
			if(coord1[0] == coord2[0]) {	//Cas Horizontal
				axeFixe = coord1[0];
				debutBateau = Math.min(coord1[1], coord2[1]);
				longueurBateau = Math.abs(coord2[1] - coord1[1])+1;
			}
			
			else {							//Cas Vertical
				axeFixe = coord1[1];
				debutBateau = Math.min(coord1[0], coord2[0]);
				longueurBateau = Math.abs(coord2[0] - coord1[0])+1;				
			}
			
			
			
			
			/*C'est ici que la vérification des cases est faite. Pour chaque case du bateau, on vérifie
			 *à la fois si la case est occupée et si les cases adjaçentes (gauche,droite,haut,bas) le sont.
			 *Le principe est le suivant : On parcoure l'ensemble des cases du bateau si il est effectivement
			 *posé aux coordonnées demandées, et pour chaque case, on utilise une seconde boucle for pour vérifier
			 *les cases adjaçentes : la boucle va de -1 à 1 et permet de vérifier la case qui précède (-1) et qui suit (+1)
			 *la case actuelle, lorsque la boucle passe par 0, elle vérifie la case actuelle elle-même. Cette itération
			 *est faite également pour l'axeFixe, afin de vérifier les cases aux extrémités. Cependant cette méthode
			 *un problème d'optimisation, car on vérifie les cases du bateau plusieurs fois.*/
			
			for (int i = 0; i < longueurBateau; i++) {
				currentBateau = debutBateau + i; //Cette boucle va permettre d'itérer autant de fois que de cases du bateau
				for (int caseAdjacente = -1; caseAdjacente <= 1; caseAdjacente++) { //Celle-là va parcourir toutes les cases adjacentes
																					//y compris la case elle-même
					
					
					/*Dans cette partie on vérifie que les cases qu'on va vérifier ne sortent pas du tableau :
					 *-On met des valeurs absolues pour être sûr de ne pas tenter d'accéder à des index négatifs
					 *-On vérifie qu'on ne va pas essayer de dépasser les limites du tableau, le cas échéant
					 *on remplace la case qu'on devait parcourir par la borne supérieure du tableau*/
					currentAdjacente = currentBateau + caseAdjacente <= BordSuperieurTableau ? 
							Math.abs(currentBateau + caseAdjacente) : BordSuperieurTableau;
							
					currentAxefixeAdjacente = axeFixe + caseAdjacente <= BordSuperieurTableau ? 
							Math.abs(axeFixe + caseAdjacente) : BordSuperieurTableau;
							
					//Ici on revérifie dans quelle orientation est le bateau, car la vérification change (axeFixe et
					//currentAdjacente sont inversés,etc...) en fonction de celle-ci
							
					if(coord1[0] == coord2[0]) { //Cas Horizontal
						
						
						//Les deux conditions séparées par un OU correspondent à la vérification des deux axes différents :
						//-Dans la 1ère on garde l'axe fixe initial et on décale l'axe variable (ce qui permet
						//de vérifier les cases à gauche et à droite de currentBateau), et inversement (... en haut
						//et en bas de currentBateau)
						if(tableau[axeFixe][currentAdjacente] == 1 || tableau[currentAxefixeAdjacente][currentBateau] == 1)
							return false;
						
					}
					
					else { //Cas Vertical
						if(tableau[currentAdjacente][axeFixe] == 1 || tableau[currentBateau][currentAxefixeAdjacente] == 1)
							return false;
					}
				}
			}
			
			//Si les coordonnées ont bien passés toutes ces vérifications, alors elles sont validées
			return true;
		}
		
		else return false;

	}
	
	
	
	/*Cette méthode permet d'ajouter un bateau dans un tableau. Elle suppose que les coordonnées sont
	 *exactes et ne les vérifie donc pas. La difficulté réside dans le fait que le joueur peut placer
	 *le bateau plus moins n'importe comment (dans les limites des régles du jeu) : horizontalement,
	 *verticalement, et le début du bateau peut être la 1ère comme la 2e coordonnée. Il faut donc 
	 *prévoir tous ces cas.*/
	
	public void ajouterBateau(Tableau tab,String type, int[] coord1, int[] coord2) {
		
		int[] remplissage = {0,0}; //étant donné qu'on passe par plusieurs "if" il est nécessaire de d'abord
								   //passer par une variable de stockage avant d'ajouter le point
		
		boolean remplissage_fini = false;
		int k = 0;
		int ecart = 0; //Permettra de savoir quelle coordonnée est le début du bateau, et donc dans quel sens remplir
					   //le tableau.
		
		while(!remplissage_fini) {
			
			if(coord1[0] == coord2[0]) { //Cas horizontal
			
				ecart = coord2[1] - coord1[1];
				remplissage[0] = coord1[0];
				
				if (ecart<0) remplissage[1] = coord1[1] - k; //Cas où la 1ère coordonnée est la fin du bateau
															 //Dans ce cas on décrémente vers le début
				else remplissage[1] = coord1[1] + k; //Cas où la 2e coordonnée est la fin du bateau
				 									 //Dans ce cas on incrémente vers la fin
			}
			
			else { //Cas horizontal
				
				ecart = coord2[0] - coord1[0];
				remplissage[1] = coord1[1];
				
				
				if (ecart<0) remplissage[0] = coord1[0] - k;
				else remplissage[0] = coord1[0] + k;

				
			}
			
			if (k >= Math.abs(ecart)) remplissage_fini = true; //On s'arrête lorsqu'on a placé autant de points que l'écart
															   //entre les 2 coordonnées
			tab.ajouter(remplissage); //A chaque itération de la boucle on ajoute le nouveau point
			k++;
		}
		
		tab.addBateau(type, coord1, coord2); // A la fin on ajoute le bateau à la liste du tableau
		
	}
	
	
	/*Cette méthode sert à gérer les attaques des joueurs. Elle permet d'ajouter le point au tableau des attaques du joueur
	 *et de vérifier si il a touché un bateau, et si oui lequel.*/
	public Bateau attaque(Tableau attaquant, Tableau cible, int[] coord) {
		
		attaquant.ajouterAttaque(coord, attaquant.pointOccupe(coord)); //Le programme ajoute les coordonnées à la liste
																	   //des points que le joueur a attaqué
		
		//On vérifie si il y a un bateau à ces coordonnées
		//et on envoie l'instance du bateau en question
		if (cible.pointOccupe(coord)) {
			cible.supprimer(coord);
			Bateau bat = cible.getBateauTouche();
			return bat;
		}
		
		else {
			return null;
		}
	}




}
