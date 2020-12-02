package BatailleNavale;

public class BatailleNavale {
	
	
	private Tableau joueur1;
	private Tableau joueur2;

	
	
	
	
	
	public BatailleNavale(Tableau joueur1, Tableau joueur2) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
	}


	public Tableau getJoueur1() {
		return joueur1;
	}


	public void setJoueur1(Tableau joueur1) {
		this.joueur1 = joueur1;
	}


	public Tableau getJoueur2() {
		return joueur2;
	}


	public void setJoueur2(Tableau joueur2) {
		this.joueur2 = joueur2;
	}

	
	

	
	public boolean verifierBateau(Tableau tab, int[] coord1, int[] coord2, String typeBateau) {
		
		//Cas de bords Bords inférieurs Fait
		//Cas vertical Fait
		//coord1[0] > coord2[0] Fait
		
		if((coord1[0] != coord2[0]) && (coord1[1] != coord2[1])) {
			return false;
		}
		
		
		if(Bateau.getLongueurBateau(typeBateau) == Math.max(Math.abs(coord1[0]-coord2[0])+1, Math.abs(coord1[1]-coord2[1])+1)) {
			
			int debutBateau;
			int longueurBateau;
			int currentBateau;
			int currentAdjacente;
			int axeFixe;
			int currentAxefixeAdjacente;
			int[][] tableau = tab.getTab();
			int bordSuperieurTableau = tableau.length - 1;
			
			
			if(coord1[0] == coord2[0]) {	//Horizontal
				axeFixe = coord1[0];
				debutBateau = Math.min(coord1[1], coord2[1]);
				longueurBateau = Math.abs(coord2[1] - coord1[1])+1;
			}
			
			else {							//Vertical
				axeFixe = coord1[1];
				debutBateau = Math.min(coord1[0], coord2[0]);
				longueurBateau = Math.abs(coord2[0] - coord1[0])+1;				
			}
			
			
			for (int i = 0; i < longueurBateau; i++) {
				currentBateau = debutBateau + i;
				for (int caseAdjacente = -1; caseAdjacente <= 1; caseAdjacente++) {
					
					currentAdjacente = currentBateau + caseAdjacente <= bordSuperieurTableau ? 
							Math.abs(currentBateau + caseAdjacente) : bordSuperieurTableau;
							
					currentAxefixeAdjacente = axeFixe + caseAdjacente <= bordSuperieurTableau ? 
							Math.abs(axeFixe + caseAdjacente) : bordSuperieurTableau;
							
					if(tableau[axeFixe][currentAdjacente] == 1 || tableau[currentAxefixeAdjacente][currentBateau] == 1) return false;
				}
			}
			
			return true;
		}
		
		else return false;

	}
	
	
	public void ajouterBateau(Tableau tab,String type, int[] coord1, String orientation) {
		
		int[] remplissage = {0,0};
		boolean remplissage_fini = false;
		int k = 0;
		int ecart = 0;
		
		while(!remplissage_fini) {
			
			if(coord1[0] == orientation[0] && coord1[1] != orientation[1]) {
			
				ecart = orientation[1] - coord1[1];
				remplissage[0] = coord1[0];
				
				if (ecart<0) remplissage[1] = coord1[1] - k;
				else remplissage[1] = coord1[1] + k;

			}
			
			else if (coord1[0] != orientation[0] && coord1[1] == orientation[1]) {
				
				ecart = orientation[0] - coord1[0];
				remplissage[1] = coord1[1];
				
				
				if (ecart<0) remplissage[0] = coord1[0] - k;
				else remplissage[0] = coord1[0] + k;

				
			}
			
			if (k >= Math.abs(ecart)) remplissage_fini = true;
			tab.ajouter(remplissage);
			k++;
		}
		
		tab.addBateau(type, coord1, orientation);
		
	}
	
	
	public Bateau attaque(Tableau tab,int[] coord) {
		tab.ajouterAttaque(coord, tab.pointOccupe(coord));
		if (tab.pointOccupe(coord)) {
			tab.supprimer(coord);
			Bateau bat = tab.getBateauTouche();
			return bat;
		}
		
		else {
			return null;
		}
	}
}
