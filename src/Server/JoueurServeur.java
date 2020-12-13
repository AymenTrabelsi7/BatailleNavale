package Server;

import java.io.IOException;
import java.net.Socket;

public class JoueurServeur {
	
	private int id;
	private String username;
	private CommunicationJoueurServeur comm;
	private ServeurBatailleNavale serveur;
	private int nbCasesRestantes;
	private boolean perdu;
	private String retry = null;

	
	public JoueurServeur(int id, Socket socket, ServeurBatailleNavale serveur, String username ) throws IOException {
		this.id = id;
		this.username = username;
		this.comm = new CommunicationJoueurServeur(this, socket);
		this.serveur = serveur;
		this.setPerdu(false);
	}
	
	public JoueurServeur(int id, Socket socket, ServeurBatailleNavale serveur) throws IOException {
		this.id = id;
		this.comm = new CommunicationJoueurServeur(this, socket);
		this.serveur = serveur;
		this.username = "default";
		this.setNbCasesRestantes(18);
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String usr) {
		this.username = usr;;
	}
	
	public ServeurBatailleNavale getServeur() {
		return serveur;
	}

	public CommunicationJoueurServeur getComm() {
		return comm;
	}
	
	public int getNbCasesRestantes() {
		return nbCasesRestantes;
	}
	
	public void setNbCasesRestantes(int nbCasesRestantes) {
		this.nbCasesRestantes = nbCasesRestantes;
	}
	
	public boolean isBateauxFinis() {
		synchronized(comm) {			
			return comm.getbateauxFinis();
		}
	}
	
	public boolean isPerdu() {
		return perdu;
	}
	
	public void setPerdu(boolean perdu) {
		this.perdu = perdu;
	}
	
	public String getRetry() {
		return retry;
	}

	public void setRetry(String retry) {
		this.retry = retry;
	}
	
	public void recevoirAttaque(String coord) {
		getComm().getHandleSend().get('i').handleRequest(coord);
	}
	
	public void sendJoueursConnectes(String connectes) {
		getComm().getHandleSend().get('c').handleRequest(connectes);
	}
	
	public void sendBateauxFinis(String fini) {
		getComm().getHandleSend().get('b').handleRequest(fini);
	}

	
	public void sendGagne(String gagnant) {
		getComm().getHandleSend().get('g').handleRequest(gagnant);
	}

	public void recevoirResultatAttaque(String result) {
		getComm().getHandleSend().get('o').handleRequest(result);
	}

	public void sendTour(String tour) {
		getComm().getHandleSend().get('t').handleRequest(tour);
	}

	public void addBateaux(String bateau) {
		serveur.addBateaux(bateau);
	}

	public void addAttaques(String attaque) {
		serveur.addAttaques(attaque);
	}

	public void demanderAttaque(JoueurServeur joueur, String coord) {
		serveur.demanderAttaque(joueur, coord);
	}

	public void envoyerResultatAttaque(JoueurServeur joueur, String result) {
		serveur.envoyerResultatAttaque(joueur, result);
	}

	

	

	
	
	
	
}
