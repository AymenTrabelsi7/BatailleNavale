package Client;

import java.io.IOException;
import java.util.Scanner;

import BatailleNavale.Bateau;
import BatailleNavale.Tableau;

public class Joueur {
	
	private Tableau tab;
	private String username;
	private int id;
	private int[] attaqueActuelle = null;
	
	public Joueur(Scanner sc) {
		this.tab = new Tableau();
		System.out.print("Entrez un nom d'utilisateur : ");
		this.username = sc.nextLine();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tableau getTab() {
		return tab;
	}

	public String getUsername() {
		return username;
	}
	
	public int[] getAttaqueActuelle() {
		return attaqueActuelle;
	}

	public void setAttaqueActuelle(int[] attaqueActuelle) {
		this.attaqueActuelle = attaqueActuelle;
	}

	public String verifierAttaque(String coord) {
		
		String result = "";
		String coule = "";
		String coordParsed = coord.split("/")[2];
		int[] coordConverted = convert(coordParsed);
		

		if(tab.pointOccupe(coordConverted)) {
			tab.supprimer(coordConverted);
			Bateau bat = tab.getBateauTouche();
			result = bat.getTypeBateau();
			System.out.print("L'adversaire a "); 
			if(bat.getNbCasesRestantes() == 0) {
				coule = "/c";
				System.out.print("coulé");
			}
			else System.out.print("touché");
			System.out.println(" votre " + result + " aux coordonnées " + coordParsed + " ! Votre tableau :");
			afficherTableau();
		}
		else {
			result = "null";
			System.out.println("L'adversaire a attaqué les coordonnées " + coordParsed + " et n'a touché aucun de vos bateaux.");//            
		}
		return result+coule;
	}
	
	public static boolean verifCoord(String coord) {
		
		if((coord.length() == 2 || coord.length() == 3) && 
			Tableau.conv.containsValue(coord.charAt(0)) ) {
			
			if(coord.length() == 3 && coord.charAt(1) == '1' && coord.charAt(2) == '0') return true;
			else if (coord.length() == 2 && Character.isDigit(coord.charAt(1))) return true;
			
		}
		
		return false;
	}
	
	
	public int[] entrerCoordonnees(Scanner sc) throws IOException {
		String s = sc.nextLine();
		while(!verifCoord(s)) {
			System.out.println("Veuillez entrer des coordonnées valides (ex A2, B10...)");
			s = sc.nextLine();
		}
		int[] res = convert(s);
		return res;
	}
	
	
	public int[] convert(String coord) {
		int[] s = new int[2];
		for(int i = 0;i<10;i++) {
			if (Tableau.conv.get(i) == coord.charAt(0)) s[0] = i;
		}
		if(coord.length() == 3) s[1] = 9;
		else s[1] = Character.getNumericValue(coord.charAt(1))-1;
		return s;
	}
	
	public String unconvert(int[] coord) {
		return Tableau.conv.get(coord[0]).toString() + (coord[1]+1);
	}
	
	
	public String afficherTableau() {
		return tab.afficher(tab.getTab());
	}
	
	public String afficherTableauAttaques() {
		return tab.afficher(tab.getTabAttaques());
	}





}
