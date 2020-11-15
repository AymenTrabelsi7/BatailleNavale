package BatailleNavale;

public class BatailleNavale {
	
	
	private Tableau joueur1;
	private Tableau joueur2;

	
	
	
	
	
	public BatailleNavale() {
		joueur1 = new Tableau();
		joueur2 = new Tableau();
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


	public void test(){
	}
	
	

	
	
	public void ajouterBateau(Tableau tab,String type, int[] coord1, int[] coord2) {
		
		int[] remplissage = {0,0};
		boolean remplissage_fini = false;
		int k = 0;
		
		
		while(!remplissage_fini) {
			
			if(coord1[0] == coord2[0] && coord1[1] != coord2[1]) {
				
				remplissage[0] = coord1[0];
				remplissage[1] = coord1[1] + k;
				tab.ajouter(remplissage);
				k++;
				
				if (coord1[1] + k > coord2[1]) remplissage_fini = true;
				
			}
			
			else if (coord1[0] != coord2[0] && coord1[1] == coord2[1]) {
				
				remplissage[1] = coord1[1];
				remplissage[0] = coord1[0] + k;
				tab.ajouter(remplissage);
				k++;
				
				if (coord1[0] + k > coord2[0]) remplissage_fini = true;
				
			}
		}
		
		tab.addBateau(type, coord1, coord2);
		
	}
	
	
	public boolean attaque(Tableau tab,int[] coord) {
		if (tab.pointOccupe(coord)) {
			tab.supprimer(coord);
			return true;
		}
		
		else {
			return false;
		}
	}
}
