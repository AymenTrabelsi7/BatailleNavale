package BatailleNavale;


public class Bateau {
	
	private String typeBateau;
	private int longueurInitiale;
	private int[] coord1,coord2;
	private boolean horizontal;
	private int nbCasesRestantes;
	
	public static String[][] typesBateaux = {{"Torpilleur","2"},{"Contre-torpilleur","3"},{"Croiseur","4"},{"Porte-avions","5"}};

	public Bateau(String typeBateau, int[] coord1, int[] coord2) {
		this.typeBateau = typeBateau;
		this.coord1 = coord1;
		this.coord2 = coord2;
		this.longueurInitiale = Math.max(coord1[0]-coord2[0], coord1[1]-coord2[1]);
		this.nbCasesRestantes = longueurInitiale;
		if(coord1[0]-coord2[0] == 0) horizontal = true;
		else horizontal = false;
	}
	
	public int getNbCasesRestantes() {
		return nbCasesRestantes;
	}
	
	
	public int getLongueurInitiale() {
		return longueurInitiale;
	}
	
	public String getTypeBateau() {
		return typeBateau;
	}
	
	public boolean touche(int[][] tab) {
		int s = 0;
		if(horizontal) {
			for(int i = coord1[1];i<=coord2[1];i++) {
				if (tab[coord1[0]][coord1[i]] == 1) s++;
			}
		}
		
		else {
			for(int i = coord1[0];i<=coord2[0];i++) {
				if (tab[coord1[i]][coord1[0]] == 1) s++;
			}
		}
		if (s == nbCasesRestantes) return false;
		else {
			nbCasesRestantes = s;
			return true;
		}
	}
	
	
}
