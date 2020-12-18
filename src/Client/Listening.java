package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listening extends Thread {
	
	private BufferedReader in;
	private Communication comm;
	private String input;
	private String[] inputParsed;
	
	public Listening(Socket serveurSocket, Communication comm) throws IOException{
		this.in = new BufferedReader(new InputStreamReader(serveurSocket.getInputStream()));
		this.comm = comm;
		this.input = null;
	}
	
	public void run() {
		try {
			while(true) {
				if(in.ready()) input = in.readLine();
				if(input != null) {					
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
