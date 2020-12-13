package BatailleNavale;

public class BatailleNavale {
	
	/*C'est dans cette classe que se trouve le processing de base concernant le jeu lui-m�me.
	 * On y trouve les m�thodes v�rifierBateau, ajouterBateau et attaque. Ces fonctions de base
	 * seront utilis�es par le Serveur pour effectuer tous les calculs dont il a besoin pour
	 * d�cider de l'avan�ement de la partie.*/
	
	
	//C'est l� que sont associ�s les tableaux des joueurs
	//Le serveur mod�lise les joueurs avec 2 choses : un flux d'I/O, et un Tableau.


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
		
		/*M�thode un peu complexe qui permet de v�rifier plusieurs choses lorsqu'un joueur propose des coordonn�es
		 *pour un de ses bateaux :
		 *-Que le bateau s'�tend sur un seul axe, c'est-�-dire qu'il est soit horizontal soit vertical, et pas les 2
		 *-Que la longueur des coordonn�es correspond � la longueur du type de bateau qu'il est cens� placer (Longueur = Nb de cases)
		 *-Qu'aucun bateau n'est pas d�j� pr�sent sur ces coordonn�es
		 *-Qu'aucun bateau ne se trouve sur les cases adja�entes (gauche,droite,haut,bas, pas les diagonales)
		 *Si et seulement si les inputs r�pondent � tous ces crit�res, alors la m�thode renvoie true, et le
		 *serveur valide la demande en ajoutant le bateau � la liste.*/
		
		
		
		
		//La m�thode commence par v�rifier si tous les points du bateau sont align�s sur un seul axe
		if((coord1[0] != coord2[0]) && (coord1[1] != coord2[1])) {
			return false;
		}
		
		
		
		//Ensuite, elle v�rifie que le bateau correspond bien � la longueur de son type
		//La m�thode statique getLongueurBateau permet de conna�tre la longueur que doit avoir le bateau
		//La valeur absolue sert � faire abstraction du sens du bateau
		//Le max est utilis� car � ce stade l'orientation du bateau n'est pas connue
		if(Bateau.getLongueurBateau(typeBateau) == Math.max(Math.abs(coord1[0]-coord2[0])+1, Math.abs(coord1[1]-coord2[1])+1)) {
			
			
			
			int debutBateau; //Sera la plus petite coordonn�e des deux points (sur l'axe sur lequel le bateau s'�tend),
							 //car ce sera plus simple d'aller du min vers le max dans la suite
			int longueurBateau;
			int currentBateau;//Contiendra la case du bateau qui sera en cours de v�rification, et sera incr�ment�e au fur
							  //et � mesure
			int currentAdjacente; //Servira � v�rifier les cases adjacentes sur l'axe variable
			int axeFixe; //Axe sur lequel le bateau ne varie pas
			int currentAxefixeAdjacente; //Servira � v�rifier les cases adjacentes sur l'axe fixe
			int[][] tableau = tab.getTab();
			int BordSuperieurTableau = tableau.length - 1; //Derni�re position du tableau, servira � �viter les
														   //IndexOutOfBoundException
			
			
			
			/*Cette partie du programme permet de d�terminer l'orientation du bateau
			 *Cela permettra de savoir quel est l'axe fixe, et l'axe variable. Ce qui
			 *permettra de savoir sur quel axe effectuer l'incr�mentation.*/
			
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
			
			
			
			
			/*C'est ici que la v�rification des cases est faite. Pour chaque case du bateau, on v�rifie
			 *� la fois si la case est occup�e et si les cases adja�entes (gauche,droite,haut,bas) le sont.
			 *Le principe est le suivant : On parcoure l'ensemble des cases du bateau si il est effectivement
			 *pos� aux coordonn�es demand�es, et pour chaque case, on utilise une seconde boucle for pour v�rifier
			 *les cases adja�entes : la boucle va de -1 � 1 et permet de v�rifier la case qui pr�c�de (-1) et qui suit (+1)
			 *la case actuelle, lorsque la boucle passe par 0, elle v�rifie la case actuelle elle-m�me. Cette it�ration
			 *est faite �galement pour l'axeFixe, afin de v�rifier les cases aux extr�mit�s. Cependant cette m�thode
			 *un probl�me d'optimisation, car on v�rifie les cases du bateau plusieurs fois.*/
			
			for (int i = 0; i < longueurBateau; i++) {
				currentBateau = debutBateau + i; //Cette boucle va permettre d'it�rer autant de fois que de cases du bateau
				for (int caseAdjacente = -1; caseAdjacente <= 1; caseAdjacente++) { //Celle-l� va parcourir toutes les cases adjacentes
																					//y compris la case elle-m�me
					
					
					/*Dans cette partie on v�rifie que les cases qu'on va v�rifier ne sortent pas du tableau :
					 *-On met des valeurs absolues pour �tre s�r de ne pas tenter d'acc�der � des index n�gatifs
					 *-On v�rifie qu'on ne va pas essayer de d�passer les limites du tableau, le cas �ch�ant
					 *on remplace la case qu'on devait parcourir par la borne sup�rieure du tableau*/
					currentAdjacente = currentBateau + caseAdjacente <= BordSuperieurTableau ? 
							Math.abs(currentBateau + caseAdjacente) : BordSuperieurTableau;
							
					currentAxefixeAdjacente = axeFixe + caseAdjacente <= BordSuperieurTableau ? 
							Math.abs(axeFixe + caseAdjacente) : BordSuperieurTableau;
							
					//Ici on rev�rifie dans quelle orientation est le bateau, car la v�rification change (axeFixe et
					//currentAdjacente sont invers�s,etc...) en fonction de celle-ci
							
					if(coord1[0] == coord2[0]) { //Cas Horizontal
						
						
						//Les deux conditions s�par�es par un OU correspondent � la v�rification des deux axes diff�rents :
						//-Dans la 1�re on garde l'axe fixe initial et on d�cale l'axe variable (ce qui permet
						//de v�rifier les cases � gauche et � droite de currentBateau), et inversement (... en haut
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
			
			//Si les coordonn�es ont bien pass�s toutes ces v�rifications, alors elles sont valid�es
			return true;
		}
		
		else return false;

	}
	
	
	
	/*Cette m�thode permet d'ajouter un bateau dans un tableau. Elle suppose que les coordonn�es sont
	 *exactes et ne les v�rifie donc pas. La difficult� r�side dans le fait que le joueur peut placer
	 *le bateau plus moins n'importe comment (dans les limites des r�gles du jeu) : horizontalement,
	 *verticalement, et le d�but du bateau peut �tre la 1�re comme la 2e coordonn�e. Il faut donc 
	 *pr�voir tous ces cas.*/
	
	public void ajouterBateau(Tableau tab,String type, int[] coord1, int[] coord2) {
		
		int[] remplissage = {0,0}; //�tant donn� qu'on passe par plusieurs "if" il est n�cessaire de d'abord
								   //passer par une variable de stockage avant d'ajouter le point
		
		boolean remplissage_fini = false;
		int k = 0;
		int ecart = 0; //Permettra de savoir quelle coordonn�e est le d�but du bateau, et donc dans quel sens remplir
					   //le tableau.
		
		while(!remplissage_fini) {
			
			if(coord1[0] == coord2[0]) { //Cas horizontal
			
				ecart = coord2[1] - coord1[1];
				remplissage[0] = coord1[0];
				
				if (ecart<0) remplissage[1] = coord1[1] - k; //Cas o� la 1�re coordonn�e est la fin du bateau
															 //Dans ce cas on d�cr�mente vers le d�but
				else remplissage[1] = coord1[1] + k; //Cas o� la 2e coordonn�e est la fin du bateau
				 									 //Dans ce cas on incr�mente vers la fin
			}
			
			else { //Cas horizontal
				
				ecart = coord2[0] - coord1[0];
				remplissage[1] = coord1[1];
				
				
				if (ecart<0) remplissage[0] = coord1[0] - k;
				else remplissage[0] = coord1[0] + k;

				
			}
			
			if (k >= Math.abs(ecart)) remplissage_fini = true; //On s'arr�te lorsqu'on a plac� autant de points que l'�cart
															   //entre les 2 coordonn�es
			tab.ajouter(remplissage); //A chaque it�ration de la boucle on ajoute le nouveau point
			k++;
		}
		
		tab.addBateau(type, coord1, coord2); // A la fin on ajoute le bateau � la liste du tableau
		
	}
	
	
	/*Cette m�thode sert � g�rer les attaques des joueurs. Elle permet d'ajouter le point au tableau des attaques du joueur
	 *et de v�rifier si il a touch� un bateau, et si oui lequel.*/
	public Bateau attaque(Tableau attaquant, Tableau cible, int[] coord) {
		
		attaquant.ajouterAttaque(coord, attaquant.pointOccupe(coord)); //Le programme ajoute les coordonn�es � la liste
																	   //des points que le joueur a attaqu�
		
		//On v�rifie si il y a un bateau � ces coordonn�es
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
