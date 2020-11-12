package BatailleNavale;

public class Tableau {
		private char[] conv= {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
		private int[][] tab;
		
		public Tableau() {
			tab = new int[10][10];
			for(int i = 0; i<10;i++) {
				for(int j = 0; j<10;j++) {
					tab[i][j] = 0;
				}
			}
		}
		
		
		public String afficher() {
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


		public int[] convert(String coord) {
			int[] s = new int[2];
			for(int i = 0;i<10;i++) {
				if (conv[i]== coord.charAt(0)) s[0] = i;
			}
			s[1] = Character.getNumericValue(coord.charAt(1));
			return s;
		}
		
		public void ajouter(String coord) {
			int[] coordonnes = convert(coord); 
			tab[coordonnes[0]][coordonnes[1]] = 1;
		}
		
		public void ajouterBrut (int[] coord) {
			tab[coord[0]][coord[1]] = 1;
		}
		
		
		public void supprimer(String coord) {
			int[] coordonnes =convert(coord); 
			tab[coordonnes[0]][coordonnes[1]] = 0;
		}
		
		public int attaque(String coord) {
			int[] coordonnes =convert(coord); 
			if (tab[coordonnes[0]][coordonnes[1]] == 1) {
				supprimer(coord);
				return 1;
			}
			
			else {
				return 0;
			}
		}
		
		
		public void ajouterVehicule(String coord1,String coord2) {
			int[] a1 = convert(coord1);
			int[] a2 = convert(coord2);
			int[] remplissage = {0,0};
			boolean remplissage_fini = false;
			int k = 0;
			while(!remplissage_fini) {
				if(a1[0] == a2[0] && a1[1] != a2[1]) {
					remplissage[0] = a1[0];
					remplissage[1] = a1[1] + k;
					ajouterBrut(remplissage);
					k++;
					if (a1[1] + k > a2[1]) remplissage_fini = true;
				}
				
				else if (a1[0] != a2[0] && a1[1] == a2[1]) {
					remplissage[1] = a1[1];
					remplissage[0] = a1[0] + k;
					ajouterBrut(remplissage);
					k++;
					if (a1[0] + k > a2[0]) remplissage_fini = true;
				}
			}
		}
		
}
