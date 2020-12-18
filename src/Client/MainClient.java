package Client;
import java.util.Scanner;
import java.util.Vector;


import BatailleNavale.BatailleNavale;
import BatailleNavale.Bateau;

import java.io.*;

public class MainClient {



	public static void main(String[] args) throws IOException, InterruptedException {

		
		Scanner sc = new Scanner(System.in);
		Joueur joueur = new Joueur(sc);
		
		System.out.println("Bonjour " + joueur.getUsername() + " ! Nous allons vous connecter avec un autre joueur pour commencer une partie.");
		BatailleNavale partie = new BatailleNavale();
		Communication flux = new Communication(joueur, partie);
		Vector<Boolean> asyncMonTour = new Vector<Boolean>(0);
		int MonTourSize = 0;
		int ResultatAttaquesSize = 0;


		boolean veutJouer = true;
		boolean verifBateau;
		int[][] tempCoordBateaux = new int[2][2];
		int[] tempCoordAttaque = new int[2];

		while(!flux.isJoueursConnectes()) {
			Thread.sleep(1000);
			System.out.print(".");
		}

		flux.sendUsername(joueur.getUsername());
		System.out.println("Joueur connecté !\nBienvenue dans le jeu de Bataille Navale, veuillez commencer à choisir l'emplacement de vos bateaux");

		while(veutJouer) {


			System.out.println("Votre Tableau : ");
			System.out.println(joueur.afficherTableau());


			for(int i = 0;i<Bateau.typesBateaux.length;i++) {		

				do {
					for(int j = 0;j<2;j++) {						
						int jinc = j+1;
						System.out.println("Emplacement du " + Bateau.typesBateaux[i][0] + " ( Point " + jinc + " ) ( Longueur : " + Bateau.typesBateaux[i][1] + " ) : ");
						tempCoordBateaux[j] = joueur.entrerCoordonnees(sc);
					}
					verifBateau = partie.verifierBateau(joueur.getTab(), tempCoordBateaux[0], tempCoordBateaux[1], Bateau.typesBateaux[i][0]);

					if(!verifBateau) System.out.println("Vous avez donné de mauvaises coordonnées. Veuillez réessayer.");
				} while (!verifBateau);

				partie.ajouterBateau(joueur.getTab(), Bateau.typesBateaux[i][0], tempCoordBateaux[0], tempCoordBateaux[1]);
				flux.getHandleSend().get('b').handleRequest(Bateau.typesBateaux[i][0]+"/"+joueur.unconvert(tempCoordBateaux[0])+","+joueur.unconvert(tempCoordBateaux[1]));//                                                        

				System.out.println("Bateau ajouté ! Votre Tableau : ");
				System.out.println(joueur.getTab().afficher(joueur.getTab().getTab()));

			}


			System.out.print("Vous avez fini d'ajouter vos bateaux !\nEn attente du serveur...");
			flux.bateauxFinis("true");


			while(!flux.isBateauxFinis()) {
				Thread.sleep(1000);
				System.out.print(".");
			}


			System.out.println("\nTout le monde est prêt !");
			System.out.println("La partie va maintenant commencer !");

			while(partie.getGagne() == null) {
				if(MonTourSize != flux.getMonTour().size()) {
					MonTourSize = flux.getMonTour().size();
					asyncMonTour = flux.getMonTour();
					if(!asyncMonTour.lastElement()) {
						System.out.println("C'est au tour de l'adversaire...");
					}
					else {
						System.out.println("C'est à votre tour ! Voici les cases que vous avez déjà attaquées :\n0 = Pas attaqué ; 1=Raté; 2=Touché");
						System.out.println(joueur.afficherTableauAttaques());
						System.out.println("Entrer les coordonnées que vous voulez attaquer :");
						tempCoordAttaque = joueur.entrerCoordonnees(sc);
						System.out.print("Vous attaquez les coordonnées " + joueur.unconvert(tempCoordAttaque) + "...");
						joueur.setAttaqueActuelle(tempCoordAttaque);
						ResultatAttaquesSize = flux.getResultatAttaques().size();
						flux.envoyerAttaque(joueur.unconvert(tempCoordAttaque));

						while(flux.getResultatAttaques().size() == ResultatAttaquesSize) {
							Thread.sleep(300);
						}


					}

				}
				Thread.sleep(3000);
			}
			
			if(partie.getGagne().equals("g")) System.out.println("La partie est terminée, vous avez gagné ! Bravo !");
			else if(partie.getGagne().equals("p")) System.out.println("La partie est terminée, vous avez perdu. Dommage !");
			System.out.println("Voulez-vous rejouer ? y/n");
			char rejouer = joueur.entrerRejouer(sc);
			if (rejouer == 'n') {
				veutJouer = false;
				flux.envoyerRejouer("false");
				System.out.println("Vous ne voulez pas rejouer. Déconnexion du serveur...");
			}
			else {
				flux.envoyerRejouer("true");
				System.out.println("Vous voulez rejouer. En attente du serveur...");
				while(partie.getRetry() == null) {
					Thread.sleep(500);
				}
				if(partie.getRetry().equals("true")) {
					System.out.println("L'adversaire veut également rejouer. Redémarrage...");
					
					
					//RESET DES VARIABLES DU MAIN
					asyncMonTour.clear();
					MonTourSize = 0;
					ResultatAttaquesSize = 0;
					veutJouer = true;
					tempCoordBateaux = new int[2][2];
					tempCoordAttaque = new int[2];

					flux.reset();
					joueur.reset();
					partie.reset();
				}
				else {
					System.out.println("L'adversaire ne veut pas rejouer. Déconnexion du serveur...");
					veutJouer = false;
				}
				
			}
			
			Thread.sleep(3000);

		}
		sc.close();
	}
}
