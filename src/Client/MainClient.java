package Client;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class MainClient {
	public static void main(String[] args) {
		try {
				Socket s = new Socket("127.0.0.1", 1500);
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				new ListeningThread(s).start();
				System.out.println("Connexion réussie!");
				Scanner sc=new Scanner(System.in);
				String message="";
				while (message!="quit") {
					message=sc.nextLine();
					out.println(message);
				}
				sc.close();
				s.close();
			} catch(Exception e) {
			// Traitement d'erreur
			}

	}
}
