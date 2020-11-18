package Server;

import java.io.BufferedReader;
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
			partie = new BatailleNavale();
			veutJouer = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public void reset() {
		
	}
	
	public void run() {
		try {
			veutJouer = true;
			while (true) {
				
				//Initialisation - Création des bateaux, etc.
				
				int[][] J1 = new int[2][2];
				int[][] J2 = new int[2][2];
				ecrire("Bienvenue dans le jeu de Bataille Navale, veuillez commencer à choisir l'emplacement de vos bateaux");
				
				while(veutJouer) {
					ecrire("Votre Tableau : ");
					out1.println(partie.getJoueur1());
					out2.println(partie.getJoueur2());
					
					for(int i = 0;i<Bateau.typesBateaux.length;i++) {		
						
						for(int j = 0;j<2;j++) {						
							int jinc = j+1;
							ecrire("Emplacement du " + Bateau.typesBateaux[i][0] + " ( Point " + jinc + " ) ( Longueur : " + Bateau.typesBateaux[i][1] + " ) : ");
							fluxJ1=in1.readLine();
							fluxJ2=in2.readLine();
							J1[j] = convert(fluxJ1);
							J2[j] = convert(fluxJ2);
						}
						
						partie.ajouterBateau(partie.getJoueur1(), Bateau.typesBateaux[i][0], J1[0], J1[1]);
						partie.ajouterBateau(partie.getJoueur2(), Bateau.typesBateaux[i][0], J2[0], J2[1]);
						
						ecrire("Bateau ajouté ! Votre Tableau : ");
						out1.println(partie.getJoueur1());
						out2.println(partie.getJoueur2());
					}
					
					ecrire("La partie va maintenant commencer");
					
					//Partie
					while(!partie.getJoueur1().perdu() && !partie.getJoueur2().perdu()) {
						
						ecrire("C'est au joueur 1 de commencer !");
						out1.println("Entrer un point de frappe : ");
						fluxJ1 = in1.readLine();
						partie.attaque(partie.getJoueur2(), convert(fluxJ1));
						//CONTINUER ICI
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