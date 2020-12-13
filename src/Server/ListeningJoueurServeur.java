package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ListeningJoueurServeur extends Thread {

	private BufferedReader in;
	private CommunicationJoueurServeur comm;
	private String input;
	private String[] inputParsed;

	public ListeningJoueurServeur(Socket joueur, CommunicationJoueurServeur comm) throws IOException {
		this.in = new BufferedReader(new InputStreamReader(joueur.getInputStream()));
		this.comm = comm;
	}

	public void run() {
		try {
			while(true) {
				if(in.ready()) input = in.readLine();
				if(input != null) {					
					//System.out.println("Listener/"+ comm.getJoueur().getUsername() + " : " + input);
					inputParsed = input.split("/");
					char query = inputParsed[0].charAt(0);
					switch (query) {
					case 'a':
						comm.getHandleReceive().get(inputParsed[1].charAt(0)).handleRequest(input);
						break;
					default:
						if(comm.getHandleReceive().containsKey(query)) {							
							comm.getHandleReceive().get(inputParsed[0].charAt(0)).handleRequest(input);
						}
						else {
							System.out.println("Listener : Unreadable : " + input);
						}
					}
				}
				input = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
