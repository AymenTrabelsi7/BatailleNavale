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
		
		public int convert(char a) {
			for(int i = 0;i<10;i++) {
				if (conv[i]== a) return i;
			}
			return 0;
		}
		
		public void ajouter(String coord) {
			tab[convert(coord.charAt(0))][(int)coord.charAt(1)] = 1;
		}
		
}
