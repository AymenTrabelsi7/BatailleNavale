package BatailleNavale;

import java.util.HashMap;
import java.util.Vector;

public class Tableau {
	
		public static HashMap<Integer,Character> conv = new HashMap<Integer,Character>(0);
		private int[][] tab;
		private int[][] tabAttaques;
		private Vector<Bateau> bateaux;
		private int nbBateaux;
		
		
		public Tableau() {
			
			tab = new int[10][10];
			tabAttaques = new int[10][10];
			for(int i = 0; i<10;i++) {
				for(int j = 0; j<10;j++) {
					tab[i][j] = 0;
					tabAttaques[i][j] = 0;
				}
			}
			
			
			
			bateaux = new Vector<Bateau>(0);
			nbBateaux = 0;
			if(conv.size() == 0) {
				
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
			}
		}
		
		
		
	
		
		public String afficher(int[][] tableau) {
			String sortie = "";
			for(int i = 1;i<11;i++) {
				sortie += " " + (i);
			}
			sortie += "\n";
			
			
			for(int i = 0; i<10;i++) {
				sortie += " ";
				for(int j = 0; j<10;j++) {
					sortie += "--";
				}
				sortie += "-\n" + conv.get(i);
				for(int j = 0; j<10;j++) {
					sortie += "|" + tableau[i][j];
				}
				sortie += "|\n";
			}
			
			
			sortie += " ";
			for(int j = 0; j<10;j++) {
				sortie += "--";
			}
			sortie += "-\n";
			
			return sortie;
		}

		
		

		
		public int[][] getTab() {
			return tab;
		}


		public int getNbBateaux() {
			return nbBateaux;
		}


		public Vector<Bateau> getBateaux() {
			return bateaux;
		}

		
		


		public int[][] getTabAttaques() {
			return tabAttaques;
		}




		void ajouter (int[] coord) {
			tab[coord[0]][coord[1]] = 1;
		}
		
		void ajouterAttaque (int[] coord, boolean touche) {
			if(touche)tabAttaques[coord[0]][coord[1]] = 2;
			else tabAttaques[coord[0]][coord[1]] = 1;
		}
		
		
		void supprimer(int[] coord) {
			tab[coord[0]][coord[1]] = 0;
		}
		

		
		public boolean pointOccupe(int[] coord) {
			if (tab[coord[0]][coord[1]] == 1) return true;
			else return false;
		}

		
		
		public boolean perdu() {
			if(bateaux.size() == 0) return true;
			else return false;
		}
		
		public void addBateau(String type, int[] coord1, int[] coord2) {
			bateaux.add(new Bateau(type,coord1,coord2));
			nbBateaux++;
		}
		
		public Bateau getBateauTouche() {
			for(int i = 0;i<bateaux.size();i++) {
				if(bateaux.get(i).touche(tab)) {
					Bateau s = bateaux.get(i);
					if(bateaux.get(i).getNbCasesRestantes()==0) bateaux.remove(i);
					return s;
				}
			}
			return null;
		}
		
		
		
		
	
}
