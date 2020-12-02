package BatailleNavale;

import java.util.HashMap;
import java.util.Vector;

public class Tableau {
		
	/*La classe Tableau	représente la grille du joueur ainsi que ses bateaux, mais également les attaques qu'il a effectué.
	  Elle constituée des méthodes de bases qui manipulent les données comme ajouter un point, ajouter un bateau à la liste,
	  etc.. C'est donc le 1er niveau d'abstraction du programme (celle de plus bas niveau)*/
	
		public static HashMap<Integer,Character> conv = new HashMap<Integer,Character>(0); //Contient par défaut les différentes lettres
																						   //correspondant aux lignes du tableau.
																						   //Cela permet de les retrouver facilement.
		private int[][] tab; // Le tableau en lui-même, 0 = bateau non présent à cette case, 1 = bateau présent
		private int[][] tabAttaques; // Tableau qui mémorise les cases qu'on a déjà attaqué
		private Vector<Bateau> bateaux; //Contient toutes les informations sur les bateaux du joueur
										//(Type, Longueur, coordonnées, nombre de cases restantes...)
		private int nbBateaux; //Nombre de Bateaux restants du joueurs, permet de savoir quand le joueur a perdu
		
		
		public Tableau() {
			
			//Le constructeur consiste simplement à initialiser la tableau à 0 (on ne sait jamais) ainsi que la liste des bateaux,
			//puis à remplir la HashMap contenant les lettres du tableau
			
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
				
				conv.put(0, 'A'); //La clé de chaque lettre correspond au numéro de ligne correspondant dans le tableau
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
		
		
		
	
		/*Méthode qui affiche le tableau. Tester dans la console pour un exemple concret.*/
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



		//Methode fondamentale qui permet d'ajouter un point au tableau. Cette méthode sert de base pour ajouter les bateaux.
		void ajouter (int[] coord) {
			tab[coord[0]][coord[1]] = 1;
		}
		
		//Ajoute un point sur le tableau des attaques. 2 = touché, 1 = raté, 0 = pas encore attaqué
		void ajouterAttaque (int[] coord, boolean touche) {
			if(touche)tabAttaques[coord[0]][coord[1]] = 2;
			else tabAttaques[coord[0]][coord[1]] = 1;
		}
		
		//Méthodes assez basiques
		
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
		
		//Ajoute un bateau au vector, en spécifiant le type de bateau, ainsi que les coordonnées. A ce moment
		//du programme les coordonnées reçues doivent être valides, il n'y a donc pas besoin de vérification.
		public void addBateau(String type, int[] coord1, int[] coord2) {
			bateaux.add(new Bateau(type,coord1,coord2));
			nbBateaux++;
		}
		
		//Est déclenchée après une attaque de l'adversaire. Permet de savoir si un bateau a été touché par l'attaque,
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
