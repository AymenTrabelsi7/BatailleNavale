package BatailleNavale;


public class Bateau {
	
	private String typeBateau;
	private int longueurInitiale;
	private int[] coord1, coord2 = new int[2];
	private boolean horizontal;
	private int nbCasesRestantes;
	
	public static String[][] typesBateaux = {{"Torpilleur","2"},{"Contre-torpilleur","3"},{"Croiseur","4"},{"Croiseur","4"},{"Porte-avions","5"}};

	public Bateau(String typeBateau, int[] coord1, boolean horizontal) {
		this.typeBateau = typeBateau;
		this.coord1 = coord1;
		for(String[] bateau : typesBateaux) {
			if(bateau[0].equals(typeBateau)) this.longueurInitiale = bateau[1];
		}
		if(horizontal) {
			this.coord2[0] = this.coord1[0];
			this.coord2[1] = this.coord1[1] + this.longueurInitiale;
		}
		this.nbCasesRestantes = longueurInitiale;
		this.horizontal = horizontal;
	}
	
	
	
	public static int getLongueurBateau(String type) {
		for (int i = 0;i<typesBateaux.length;i++) {
			if(type.equals(typesBateaux[i][0])) return Integer.parseInt(typesBateaux[i][1]);
		}
		
		return -1;
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
				if (tab[coord1[0]][coord1[1]+i] == 1) s++;
			}
		}
		
		else {
			for(int i = coord1[0];i<=coord2[0];i++) {
				if (tab[coord1[0]+i][coord1[1]] == 1) s++;
			}
		}
		if (s == nbCasesRestantes) return false;
		else {
			nbCasesRestantes = s;
			return true;
		}
	}
	
	
}
