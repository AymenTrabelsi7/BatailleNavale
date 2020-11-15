package BatailleNavale;

import java.util.Vector;

public class Tableau {
		public static char[] conv= {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
		private int[][] tab;
		private Vector<Bateau> bateaux;
		private int nbBateaux;
		
		
		public Tableau() {
			tab = new int[10][10];
			for(int i = 0; i<10;i++) {
				for(int j = 0; j<10;j++) {
					tab[i][j] = 0;
				}
			}
			bateaux.setSize(0);
			nbBateaux = 0;
		}
		
		
		
		
		public String toString() {
			String sortie = " ";
			
			
			for(int i = 1;i<11;i++) {
				sortie += " " + (i);
			}
			sortie += "\n";
			
			
			for(int i = 0; i<10;i++) {
				sortie += " ";
				for(int j = 0; j<10;j++) {
					sortie += "--";
				}
				sortie += "-\n" + conv[i];
				for(int j = 0; j<10;j++) {
					sortie += "|" + tab[i][j];
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

		



		void ajouter (int[] coord) {
			tab[coord[0]][coord[1]] = 1;
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
