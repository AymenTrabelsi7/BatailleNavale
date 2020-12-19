package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServeurBatailleNavale extends Thread {
	

	private ServerSocket salon;
	private int port;
	private int idpartie;
	private Vector<String> attaques;
	private Vector<String> bateaux;
	public JoueurServeur j1;
	public JoueurServeur j2;
	private boolean tourFini;
	private boolean rejouer;
	
	public void log(String msg) {
		System.out.println("[Salon " + getIdpartie() + "] Log : "+ msg);
	}

	public ServeurBatailleNavale(int id, int port) {
		try {
			this.port = port;
			this.idpartie = id;
			log("Nouveau salon sur le port " + port + " prêt à accueillir les joueurs !");
			salon = new ServerSocket(port);
			Socket client1 = salon.accept();
			Socket client2 = salon.accept();
			
			j1 = new JoueurServeur((int) Math.random()*100000, client1,this);
			log("Un joueur s'est connecté !");
			j2 = new JoueurServeur((int) Math.random()*100000, client2,this);
			log("Les 2 Joueurs se sont connectés !");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		attaques = new Vector<String>(0);
		bateaux = new Vector<String>(0);
		rejouer = true;
		
		
	}



	public void reset() {
		attaques.clear();
		bateaux.clear();
		j1.reset();
		j2.reset();
	}

	public void run() {
		try {
			j1.sendJoueursConnectes("true");
			j2.sendJoueursConnectes("true");
			while(rejouer) {				
				while(!j1.isBateauxFinis() || !j2.isBateauxFinis()) {Thread.sleep(500);}
				log("Bateaux des deux joueurs terminés, la partie peut commencer");
				j1.sendBateauxFinis("true");
				j2.sendBateauxFinis("true");
				
				while(!j1.isPerdu() && !j2.isPerdu()) {
					//Envoyer Tour J1
					j1.sendTour("true");
					j2.sendTour("false");
					tourFini = false;
					//Savoir quand J1 a fini
					while(!isTourFini()) {Thread.sleep(500);}
					
					//Envoyer Tour J2
					if(!j2.isPerdu()) {					
						j1.sendTour("false");
						j2.sendTour("true");
						tourFini = false;
						//Savoir quand J2 a fini
						while(!isTourFini()) {Thread.sleep(500);}
					}
				}
				String gagne = "Log : La partie est finie ! ";
				gagne += j1.isPerdu() ? j2.getUsername() : j1.getUsername();
				log(gagne + " a gagné !");
				j1.sendGagne(String.valueOf(!j1.isPerdu()));
				j2.sendGagne(String.valueOf(!j2.isPerdu()));
				
				while(j1.getRetry() == null || j2.getRetry() == null) {Thread.sleep(500);}
				if(j1.getRetry().equals("false") || j2.getRetry().equals("false")) {
					log("Un des joueurs ne veut pas recommencer. La partie va s'arrêter...");
					rejouer = false;
				}
				else log("Les deux joueurs veulent recommencer. La partie va redémarrer...");
				j1.sendRejouer(rejouer ? "true" : "false");
				j2.sendRejouer(rejouer ? "true" : "false");
				reset();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public Vector<String> getAttaques() {
		return attaques;
	}
	
	public void demanderAttaque(JoueurServeur demandeur,String coord) {
		if (demandeur == j1) j2.recevoirAttaque(coord);
		else if(demandeur == j2) j1.recevoirAttaque(coord);
	}



	public Vector<String> getBateaux() {
		return bateaux;
	}



	public boolean isTourFini() {
		return tourFini;
	}



	public void envoyerResultatAttaque(JoueurServeur expediteur, String result) {
		if (expediteur == j1) {
			j2.recevoirResultatAttaque(result);
		}
		else if(expediteur == j2) {
			j1.recevoirResultatAttaque(result);
		}
		tourFini = true;
	}



	public void addBateaux(String bateau) {
		bateaux.add(bateau);
	}



	public void addAttaques(String attaque) {
		attaques.add(attaque);
	}



	public int getPort() {
		return port;
	}



	public int getIdpartie() {
		return idpartie;
	}
	
	
}