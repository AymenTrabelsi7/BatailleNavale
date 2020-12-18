package Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import BatailleNavale.BatailleNavale;
import Common.RequeteIntf;

public class Communication extends Thread {
	
	private Socket serveurSocket;
	private PrintWriter out;
	@SuppressWarnings("unused")
	private Joueur joueur;
	@SuppressWarnings("unused")
	private BatailleNavale partie;
	private HashMap<Character,RequeteIntf> handleReceive;
	private HashMap<Character,RequeteIntf> handleSend;
	private boolean joueursConnectes;
	private boolean bateauxFinis;
	private Vector<Boolean> monTour;
	private Vector<String> resultatAttaques;
	
	public Communication(Joueur joueur, BatailleNavale partie) {
		
		
		
		try {
			this.serveurSocket = new Socket("127.0.0.1", 1500);
			this.out = new PrintWriter(serveurSocket.getOutputStream(), true);
			new Listening(serveurSocket,this).start();
		} catch (Exception e) {
			System.out.println("Connexion au serveur échouée.");
			e.printStackTrace();
		}
		System.out.println("Connexion au serveur réussie ! En attente du serveur...");
		
		
		this.joueur = joueur;
		this.partie = partie;
		this.handleReceive = new HashMap<Character,RequeteIntf>(0);
		this.handleSend = new HashMap<Character,RequeteIntf>(0);
		this.monTour = new Vector<Boolean>(0);
		this.resultatAttaques = new Vector<String>(0);
		this.joueursConnectes = false;
		this.bateauxFinis = false;
		
		/*PARTIE HANDLERS RECIEVE*/
		
		handleReceive.put('c', new RequeteIntf() {
			public void handleRequest(String request) {
				String parsed = request.split("/")[1];
				if(parsed.equals("true")) {
					joueursConnectes = true;
				}
				else if(parsed.equals("false")) joueursConnectes = false;
				
			}
		});
		
		handleReceive.put('b', new RequeteIntf() {
			public void handleRequest(String request) {
				bateauxFinis = true;
			}
		});
		
		
		handleReceive.put('t', new RequeteIntf() {
			public void handleRequest(String request) {
				String[] parsed = request.split("/");
				boolean tour = parsed[1].equals("true") ? true : false;
				monTour.add(tour);
			}
		});
		
		
		handleReceive.put('i', new RequeteIntf() {
			public void handleRequest(String request) {
				String result = joueur.verifierAttaque(request);
				handleSend.get('o').handleRequest(result);
			}
		});
		
		handleReceive.put('o', new RequeteIntf() {
			public void handleRequest(String request) {
				String result = request.split("/")[2];
				int[] attaque = joueur.getAttaqueActuelle();
				
				if(!result.equals("null"))  {
					System.out.print("\nL'attaque a été réussie !\nVous avez ");
					if(request.split("/").length == 4) System.out.print("coulé");
					else System.out.print("touché");
					System.out.println(" un " + result + " !");
					joueur.getTab().ajouterAttaque(attaque, true);
				}
				
				else {
					System.out.println("\nL'attaque a échoué.\nVous n'avez touché aucun bateau.");
					joueur.getTab().ajouterAttaque(attaque, false);
				}
				
				resultatAttaques.add(result);
				joueur.setAttaqueActuelle(null);
				
			}
		});
		
		handleReceive.put('g', new RequeteIntf() {
			public void handleRequest(String request) {
				String parsed = request.split("/")[1];
				if(parsed.equals("true")) partie.setGagne("g");
				else partie.setGagne("p");
			}
		});
		
		handleReceive.put('r', new RequeteIntf() {
			public void handleRequest(String request) {
				String retry = request.split("/")[1];
				partie.setRetry(retry);
			}
		});
		
		
		
		
		
		
		
		
		/*PARTIE HANDLERS SEND*/
		
		
		
		
		handleSend.put('b', new RequeteIntf() {

			public void handleRequest(String request) {
					out.println("b/"+request);
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
		
		handleSend.put('u', new RequeteIntf() {

			public void handleRequest(String request) {
				out.println("u/"+request);
			}
			
		});
		
		handleSend.put('r', new RequeteIntf() {
			public void handleRequest(String request) {
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

	public boolean isJoueursConnectes() {
		return joueursConnectes;
	}

	public boolean isBateauxFinis() {
		return bateauxFinis;
	}

	public Vector<Boolean> getMonTour() {
		synchronized(monTour) {			
			return monTour;
		}
	}
	
	public void bateauxFinis(String fini) {
		handleSend.get('b').handleRequest(fini);
	}
	
	public void sendUsername(String usr) {
		handleSend.get('u').handleRequest(usr);
	}
	
	public void envoyerAttaque(String coord) {
		handleSend.get('i').handleRequest(coord);
	}

	public Vector<String> getResultatAttaques() {
		synchronized(resultatAttaques) {			
			return resultatAttaques;
		}
	}

	public void setResultatAttaques(Vector<String> resultatAttaques) {
		this.resultatAttaques = resultatAttaques;
	}

	public void envoyerRejouer(String rejouer) {
		handleSend.get('r').handleRequest(rejouer);
	}

	public void reset() {
		monTour = new Vector<Boolean>(0);
		resultatAttaques = new Vector<String>(0);
		bateauxFinis = false;
	}
	
}
