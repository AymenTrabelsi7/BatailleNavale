package Test;

import java.io.IOException;
import java.util.HashMap;

import BatailleNavale.Bateau;
import BatailleNavale.Tableau;

public class Sandbox {
	
	public static HashMap<Integer,Character> conv = new HashMap<Integer,Character>(0);
	public static Tableau tab = new Tableau();
	
	
	public static boolean verifierBateau(Tableau tab, int[] coord1, int[] coord2, String typeBateau) {
		
		//Cas de bords Bords inférieurs Fait
		//Cas vertical Fait
		//coord1[0] > coord2[0] Fait
		
		if((coord1[0] != coord2[0]) && (coord1[1] != coord2[1])) {
			System.out.println("Ni vertical ni horizontal");
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
			int BordSuperieurTableau = tableau.length - 1;
			
			
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
					
					currentAdjacente = currentBateau + caseAdjacente <= BordSuperieurTableau ? 
							Math.abs(currentBateau + caseAdjacente) : BordSuperieurTableau;
							
					currentAxefixeAdjacente = axeFixe + caseAdjacente <= BordSuperieurTableau ? 
							Math.abs(axeFixe + caseAdjacente) : BordSuperieurTableau;
							
					if(tableau[axeFixe][currentAdjacente] == 1 || tableau[currentAxefixeAdjacente][currentBateau] == 1) {
						System.out.println("Case adjacente ou superposée");
						return false;
					}
				}
			}
			
			return true;
		}
		
		else  {
			System.out.println("Pas la bonne longueur");
			return false;
		}

	}
	
	public static int[] convert(String coord) {
		int[] s = new int[2];
		for(int i = 0;i<10;i++) {
			if (Tableau.conv.get(i) == coord.charAt(0)) s[0] = i;
		}
		if(coord.length() == 3) s[1] = 9;
		else s[1] = Character.getNumericValue(coord.charAt(1))-1;
		return s;
	}
	
	public static void main(String[] args) throws IOException {
		conv.put(0, 'A');
		conv.put(1, 'B');
		conv.put(2, 'C');
		conv.put(3, 'D');
		conv.put(4, 'E');
		conv.put(5, 'F');
		conv.put(6, 'G');
		conv.put(7, 'H');
		conv.put(8, 'I');
		conv.put(9, 'J');
		tab.getTab()[1][2] = 1;
		int[] s1 = {0,9};
		int[] s2 = {4,9};
		System.out.println("0,9 : " + convert("E10")[0] + convert("E10")[1]);
		System.out.println(verifierBateau(tab, s1, s2,"Porte-avions"));
	}
}
