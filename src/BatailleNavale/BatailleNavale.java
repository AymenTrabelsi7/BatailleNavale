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

	
	

	
	
	public void ajouterBateau(Tableau tab,String type, int[] coord1, int[] coord2) {
		
		int[] remplissage = {0,0};
		boolean remplissage_fini = false;
		int k = 0;
		int ecart = 0;
		
		while(!remplissage_fini) {
			
			if(coord1[0] == coord2[0] && coord1[1] != coord2[1]) {
			
				ecart = coord2[1] - coord1[1];
				remplissage[0] = coord1[0];
				
				if (ecart<0) remplissage[1] = coord1[1] - k;
				else remplissage[1] = coord1[1] + k;

			}
			
			else if (coord1[0] != coord2[0] && coord1[1] == coord2[1]) {
				
				ecart = coord2[0] - coord1[0];
				remplissage[1] = coord1[1];
				
				
				if (ecart<0) remplissage[0] = coord1[0] - k;
				else remplissage[0] = coord1[0] + k;

				
			}
			
			if (k >= Math.abs(ecart)) remplissage_fini = true;
			tab.ajouter(remplissage);
			k++;
		}
		
		tab.addBateau(type, coord1, coord2);
		
	}
	
	
	public Bateau attaque(Tableau tab,int[] coord) {
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
