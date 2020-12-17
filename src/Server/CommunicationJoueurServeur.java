package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import Common.RequeteIntf;

public class CommunicationJoueurServeur {

	
	@SuppressWarnings("unused")
	private CommunicationJoueurServeur thisComm;
	public JoueurServeur joueur;
	public ServeurBatailleNavale serveur;
	@SuppressWarnings("unused")
	private Socket joueurSocket;
	private PrintWriter out;
	private HashMap<Character,RequeteIntf> handleReceive;
	private HashMap<Character,RequeteIntf> handleSend;
	private boolean joueursConnectes;
	private boolean bateauxFinis;

	public CommunicationJoueurServeur(JoueurServeur joueur, Socket joueurSocket) throws IOException {
		
		this.thisComm = this;
		this.joueur = joueur;
		this.serveur = joueur.getServeur();
		this.out = new PrintWriter(joueurSocket.getOutputStream(), true);

		try {
			new ListeningJoueurServeur(joueurSocket,this).start();
		} catch (Exception e) {
			System.out.println("Connexion au client échouée.");
			e.printStackTrace();
		}
		System.out.println("Connexion réussie !");


		this.handleReceive = new HashMap<Character,RequeteIntf>(0);
		this.handleSend = new HashMap<Character,RequeteIntf>(0);

		/*PARTIE HANDLERS RECIEVE*/

		handleReceive.put('b', new RequeteIntf() {
			public void handleRequest(String request) {
				String result = request.split("/")[1];
				if(result.equals("true")) {
					bateauxFinis = true;
					System.out.println("Log : Les bateaux de " + joueur.getUsername() + " sont placés !");
				}
				else if(result.equals("false")) bateauxFinis = false;
				else {
					joueur.addBateaux(joueur.getUsername() + "/" + request.split("/")[1] + request.split("/")[2]);//                                        
				}
			}
		});

		handleReceive.put('i', new RequeteIntf() {
			public void handleRequest(String request) {
				String coord = request.split("/")[2];
				joueur.demanderAttaque(joueur, coord);
			}
		});


		handleReceive.put('o', new RequeteIntf() {
			public void handleRequest(String request) {
				String result = request.split("/")[2];
				String resultTotal;
				if(request.split("/").length == 4) {
					resultTotal = result + "/" + request.split("/")[3];
				}
				else resultTotal = result;
				joueur.addAttaques(joueur.getUsername() + "/" + result);
				if(!result.equals("null")) {
					joueur.setNbCasesRestantes(joueur.getNbCasesRestantes()-1);
					if(joueur.getNbCasesRestantes() <= 0) joueur.setPerdu(true);
				}
				joueur.envoyerResultatAttaque(joueur,resultTotal);
			}
		});
		
		handleReceive.put('u', new RequeteIntf() {
			public void handleRequest(String request) {
				String usr = request.split("/")[1];
				joueur.setUsername(usr);
				System.out.println("Log : Utilisateur " + usr + " ajouté !");
			}
		});
		
		handleReceive.put('r', new RequeteIntf() {
			public void handleRequest(String request) {
				String retry = request.split("/")[1];
				System.out.println("DEBUG : Retry Response reçue du joueur " + joueur.getUsername() + " : " + retry);
				joueur.setRetry(retry);
			}
		});







		/*PARTIE HANDLERS SEND*/

		handleSend.put('c', new RequeteIntf() {

			public void handleRequest(String request) {
				out.println("c/" + request);
			}

		});

		handleSend.put('b', new RequeteIntf() {

			public void handleRequest(String request) {
				out.println("b/"+request);
			}

		});

		handleSend.put('t', new RequeteIntf() {

			public void handleRequest(String request) {
				out.println("t/"+request);
			}

		});
		
		handleSend.put('i', new RequeteIntf() {

			public void handleRequest(String request) {
				out.println("a/i/"+request);
			}

		});
		
		handleSend.put('o', new RequeteIntf() {

			public void handleRequest(String request) {
				out.println("a/o/"+request);
			}

		});
		
		handleSend.put('g', new RequeteIntf() {

			public void handleRequest(String request) {
				out.println("g/"+request);
			}

		});
		
		handleSend.put('r', new RequeteIntf() {
			public void handleRequest(String request) {
				System.out.println("DEBUG : Retry Response envoyée au joueur " + joueur.getUsername() + " : " + request);
				out.println("r/"+request);
			}
		});


	}

	public HashMap<Character, RequeteIntf> getHandleReceive() {
		return handleReceive;
	}

	public HashMap<Character, RequeteIntf> getHandleSend() {
		return handleSend;
	}

	public JoueurServeur getJoueur() {
		return joueur;
	}

	public boolean isJoueursConnectes() {
		return joueursConnectes;
	}

	public boolean isBateauxFinis() {
		return bateauxFinis;
	}

	public boolean getbateauxFinis() {
		return bateauxFinis;
	}

	public void reset() {
		bateauxFinis = false;
	}

}


	
