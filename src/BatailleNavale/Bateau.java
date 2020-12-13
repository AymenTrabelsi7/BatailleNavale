package BatailleNavale;


public class Bateau {
	
	/*Cette classe est la repr�sentation d'un bateau sur le plateau d'un joueur, elle contient tous les �l�ments qui
	  sont n�cessaires pour le processing : type,coordonn�es,longueur,nb de cases restantes et orientation. La classe 
	  contient �galement un tableau statique accessible par toutes les classes recensant les types de bateaux ainsi que
	  leur taille.*/
	
	private String typeBateau;
	private int longueurInitiale;
	private int[] coord1,coord2;
	private boolean horizontal;
	private int nbCasesRestantes;
	
	public static String[][] typesBateaux = {{"Torpilleur","2"},{"Contre-torpilleur","3"},{"Croiseur","4"},{"Croiseur","4"},
											 {"Porte-avions","5"}};
	
	

	public Bateau(String typeBateau, int[] coord1, int[] coord2) {
		this.typeBateau = typeBateau;
		this.coord1 = coord1;
		this.coord2 = coord2;
		this.longueurInitiale = Math.max(coord1[0]-coord2[0], coord1[1]-coord2[1]);
		this.nbCasesRestantes = longueurInitiale;
		if(coord1[0]-coord2[0] == 0) horizontal = true;
		else horizontal = false;
	}
	
	
	//M�thode statique permettant de conna�tre la longueur d'un type de bateau
	public static int getLongueurBateau(String type) {
		for (int i = 0;i<typesBateaux.length;i++) {
			if(type.equals(typesBateaux[i][0])) return Integer.parseInt(typesBateaux[i][1]);
		}
		
		return -1;
	}
	
	//Getters
	public int getNbCasesRestantes() {
		return nbCasesRestantes;
	}
	
	
	public int getLongueurInitiale() {
		return longueurInitiale;
	}
	
	public String getTypeBateau() {
		return typeBateau;
	}
	
	
	//Lorsque un point est plac� sur un tableau, celui-ci ne m�morise pas le type de bateau
	//pr�sent sur cette case. C'est pour �a que j'ai cr�� cette m�thode qui permet � chaque
	//instance de bateau de savoir si il a �t� touch� par la derni�re attaque qui a lieu. Le
	//cas �ch�ant, il renvoie true et synchronise son attribut nbCasesRestantes avec la r�alit�.
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
