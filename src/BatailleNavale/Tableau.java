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
			String sortie = "";
			for(int i = 0; i<10;i++) {
				for(int j = 0; j<10;j++) {
					sortie += "--";
				}
				sortie += "-\n";
				for(int j = 0; j<10;j++) {
					sortie += "|" + tab[i][j];
				}
				sortie += "|\n";
			}
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
			int[] coordonnes =convert(coord); 
			tab[coordonnes[0]][coordonnes[1]] = 1;
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
		
		
}
