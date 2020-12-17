package BatailleNavale;


public class Bateau {
	
	/*Cette classe est la représentation d'un bateau sur le plateau d'un joueur, elle contient tous les éléments qui
	  sont nécessaires pour le processing : type,coordonnées,longueur,nb de cases restantes et orientation. La classe 
	  contient également un tableau statique accessible par toutes les classes recensant les types de bateaux ainsi que
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
		this.longueurInitiale = Math.max(Math.abs(coord1[0]-coord2[0])+1, Math.abs(coord1[1]-coord2[1])+1);
		this.nbCasesRestantes = longueurInitiale;
		if(coord1[0]-coord2[0] == 0) horizontal = true;
		else horizontal = false;
	}
	
	
	//Méthode statique permettant de connaître la longueur d'un type de bateau
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
	
	
	//Lorsque un point est placé sur un tableau, celui-ci ne mémorise pas le type de bateau
	//présent sur cette case. C'est pour ça que j'ai créé cette méthode qui permet à chaque
	//instance de bateau de savoir si il a été touché par la dernière attaque qui a lieu. Le
	//cas échéant, il renvoie true et synchronise son attribut nbCasesRestantes avec la réalité.
	public boolean touche(int[] coord) {
		int debut,fin,axeFixe;
		
		
		if(horizontal) {
			debut = Math.min(coord1[1], coord2[1]);
			fin = debut == coord1[1] ? coord2[1] : coord1[1];
			axeFixe = coord1[0];
			if(coord[0] == axeFixe && coord[1] >= debut && coord[1] <= fin) {
				nbCasesRestantes--;
				return true;
			}
			
			else {
				return false;
			}
		}
		
		else {
			debut = Math.min(coord1[0], coord2[0]);
			fin = debut == coord1[0] ? coord2[0] : coord1[0];
			axeFixe = coord1[1];
			if(coord[1] == axeFixe && coord[0] >= debut && coord[0] <= fin) {
				nbCasesRestantes--;
				return true;
			}
			
			else {
				return false;
			}
		}
		

	}
	
	
}
