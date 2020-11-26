package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import BatailleNavale.BatailleNavale;
import BatailleNavale.Bateau;
import BatailleNavale.Tableau;

public class ThreadBataille extends Thread {
	int idpartie;
	BufferedReader in1,in2;
	PrintWriter out1,out2;
	BatailleNavale partie;
	String fluxJ1;
	String fluxJ2;
	boolean veutJouer;
	
	public void ecrire(String msg) {
		out1.println(msg);
		out2.println(msg);
	}
	
	
	public ThreadBataille(int id, Socket client1, Socket client2) {
		try {
			this.idpartie = id;
			in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			out1 = new PrintWriter(client1.getOutputStream(), true);
			in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
			out2 = new PrintWriter(client2.getOutputStream(), true);
			ecrire("Id de la partie="+idpartie+"\n");
			out1.println("Joueur 1");
			out2.println("Joueur 2");
			Tableau joueur1 = new Tableau();
			Tableau joueur2 = new Tableau();
			partie = new BatailleNavale(joueur1,joueur2);
			veutJouer = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean verifCoord(String coord) {
		
		//Autre implémentation plus safe en cas de non fonctionnement de la principale :
		
		/*if((coord.length() == 2 || coord.length() == 3)) {
			if(Tableau.conv.containsValue(coord.charAt(0))) {
				if(coord.length() == 3) {
					if(coord.charAt(1) == '1' && coord.charAt(2) == '0') return true;
					else return false;
				}
				else if (Character.isDigit(coord.charAt(1))) return true;
				else return false;
			}
			
			else return false;
		}
		else return false;*/
		
		
		
		if((coord.length() == 2 || coord.length() == 3) && Tableau.conv.containsValue(coord.charAt(0)) ) {
			if(coord.length() == 3 && coord.charAt(1) == '1' && coord.charAt(2) == '0') return true;
			else if (coord.length() == 2 && Character.isDigit(coord.charAt(1))) return true;
		}
		return false;
	}
	
	
	public int[] entrerCoordonnees(BufferedReader in, PrintWriter out) throws IOException {
		String s = in.readLine();
		
		while(!verifCoord(s)) {
			out.println("Veuillez entrer des coordonnées valides (ex A2, B10...)");
			s = in.readLine();
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
	
	public void reset() {
		//Reset toutes les variables, objets....
	}
	
	public void run() {
		try {
			veutJouer = true;
			while (true) {
				
				//Initialisation - Création des bateaux, etc.
				
				int[][] J1 = new int[2][2];
				int[][] J2 = new int[2][2];
				ecrire("Bienvenue dans le jeu de Bataille Navale, veuillez commencer à choisir l'emplacement de vos bateaux");
				String resultat = null;
				
				while(veutJouer) {
					ecrire("Votre Tableau : ");
					out1.println(partie.getJoueur1());
					out2.println(partie.getJoueur2());
					
					for(int i = 0;i<Bateau.typesBateaux.length;i++) {		
						
						for(int j = 0;j<2;j++) {						
							int jinc = j+1;
							ecrire("Emplacement du " + Bateau.typesBateaux[i][0] + " ( Point " + jinc + " ) ( Longueur : " + Bateau.typesBateaux[i][1] + " ) : ");
							J1[j] = entrerCoordonnees(in1, out1);
							J2[j] = entrerCoordonnees(in2, out2);
						}
						
						partie.ajouterBateau(partie.getJoueur1(), Bateau.typesBateaux[i][0], J1[0], J1[1]);
						partie.ajouterBateau(partie.getJoueur2(), Bateau.typesBateaux[i][0], J2[0], J2[1]);
						
						ecrire("Bateau ajouté ! Votre Tableau : ");
						out1.println(partie.getJoueur1());
						out2.println(partie.getJoueur2());
					}
					
					ecrire("La partie va maintenant commencer");
					
					int[] J1Attaque = new int[2];
					int[] J2Attaque = new int[2];

					//Partie
					while(!partie.getJoueur1().perdu() && !partie.getJoueur2().perdu()) {
						
						resultat = "";
						
						ecrire("C'est au joueur 1 de jouer !");
						out1.println("Entrer un point de frappe : ");
						J1Attaque = entrerCoordonnees(in1, out1);
						Bateau batj1 = partie.attaque(partie.getJoueur2(),J1Attaque);
						ecrire("Le Joueur 1 attaque le point " + unconvert(J1Attaque) + " !");
						
						if(batj1 != null) {
							resultat += "Un " + batj1.getTypeBateau() + " a été touché";
							if(batj1.getNbCasesRestantes() == 0) resultat += " ET coulé";
						}
						else {
							resultat += "Aucun bateau n'a été touché ";
						}
						
						resultat += " aux coordonnées " + unconvert(J1Attaque) + ".\n";
						
						ecrire(resultat);
						resultat = "";
						
						ecrire("C'est au joueur 2 de jouer !");
						out2.println("Entrer un point de frappe : ");
						J2Attaque = entrerCoordonnees(in2, out2);
						Bateau batj2 = partie.attaque(partie.getJoueur1(), J2Attaque);
						
						ecrire("Le Joueur 2 attaque le point " +  unconvert(J2Attaque) + " !");
						
						if(batj2 != null) {
							resultat += "Un " + batj2.getTypeBateau() + " a été touché";
							if(batj2.getNbCasesRestantes() == 0) resultat += " ET coulé ";
							resultat += "aux coordonnées " + unconvert(J2Attaque) + ".\n";
						}
						else {
							resultat += "Aucun bateau n'a été touché aux coordonnées " + unconvert(J2Attaque) + ".\n";
						}
						
						ecrire(resultat);
						resultat = "";
						
					}
					
					
					//Fin et proposition de rejouer
					int gagnant = 0;
					if(partie.getJoueur1().perdu()) gagnant = 2;
					else gagnant = 1;
					ecrire("C'est fini ! Le joueur " + gagnant + " a gagné ! Voulez-vous rejouer ? (y/n)");
					
					fluxJ1=in1.readLine();
					fluxJ2=in2.readLine();
					
					if(!(fluxJ1.equals("y") && fluxJ2.equals("y"))) {
						veutJouer = false;
						ecrire("Tout le monde ne veut pas rejouer. Fermeture du programme...");
					}
					
					reset();
				}
				

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}