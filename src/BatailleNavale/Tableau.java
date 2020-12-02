package BatailleNavale;

import java.util.HashMap;
import java.util.Vector;

public class Tableau {
		
	/*La classe Tableau	repr�sente la grille du joueur ainsi que ses bateaux, mais �galement les attaques qu'il a effectu�.
	  Elle constitu�e des m�thodes de bases qui manipulent les donn�es comme ajouter un point, ajouter un bateau � la liste,
	  etc.. C'est donc le 1er niveau d'abstraction du programme (celle de plus bas niveau)*/
	
		public static HashMap<Integer,Character> conv = new HashMap<Integer,Character>(0); //Contient par d�faut les diff�rentes lettres
																						   //correspondant aux lignes du tableau.
																						   //Cela permet de les retrouver facilement.
		private int[][] tab; // Le tableau en lui-m�me, 0 = bateau non pr�sent � cette case, 1 = bateau pr�sent
		private int[][] tabAttaques; // Tableau qui m�morise les cases qu'on a d�j� attaqu�
		private Vector<Bateau> bateaux; //Contient toutes les informations sur les bateaux du joueur
										//(Type, Longueur, coordonn�es, nombre de cases restantes...)
		private int nbBateaux; //Nombre de Bateaux restants du joueurs, permet de savoir quand le joueur a perdu
		
		
		public Tableau() {
			
			//Le constructeur consiste simplement � initialiser la tableau � 0 (on ne sait jamais) ainsi que la liste des bateaux,
			//puis � remplir la HashMap contenant les lettres du tableau
			
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
				
				conv.put(0, 'A'); //La cl� de chaque lettre correspond au num�ro de ligne correspondant dans le tableau
				conv.put(1, 'B'); //(j'aurais pu faire un Tableau mais j'aime bien les vecteurs)
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
		
		
		
	
		/*M�thode qui affiche le tableau. Tester dans la console pour un exemple concret.*/
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

		
		

		//Getters utiles
		
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



		//Methode fondamentale qui permet d'ajouter un point au tableau. Cette m�thode sert de base pour ajouter les bateaux.
		void ajouter (int[] coord) {
			tab[coord[0]][coord[1]] = 1;
		}
		
		//Ajoute un point sur le tableau des attaques. 2 = touch�, 1 = rat�, 0 = pas encore attaqu�
		void ajouterAttaque (int[] coord, boolean touche) {
			if(touche)tabAttaques[coord[0]][coord[1]] = 2;
			else tabAttaques[coord[0]][coord[1]] = 1;
		}
		
		//M�thodes assez basiques
		
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
		
		//Ajoute un bateau au vector, en sp�cifiant le type de bateau, ainsi que les coordonn�es. A ce moment
		//du programme les coordonn�es re�ues doivent �tre valides, il n'y a donc pas besoin de v�rification.
		public void addBateau(String type, int[] coord1, int[] coord2) {
			bateaux.add(new Bateau(type,coord1,coord2));
			nbBateaux++;
		}
		
		//Est d�clench�e apr�s une attaque de l'adversaire. Permet de savoir si un bateau a �t� touch� par l'attaque,
		//et si oui lequel.
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
