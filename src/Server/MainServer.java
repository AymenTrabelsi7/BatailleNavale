package Server;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer {
	public static void main(String[] args) {
		try {
			int port = 1501;
			@SuppressWarnings("resource")
			ServerSocket ecoute = new ServerSocket(1500);
			PrintWriter out1,out2;
			
			System.out.println("[SC] Serveur central lancé!");

			int id=1;	
			while(true) {
				Socket client1 = ecoute.accept();
				Socket client2 = ecoute.accept();

				
				out1 = new PrintWriter(client1.getOutputStream(), true);
				out2 = new PrintWriter(client2.getOutputStream(), true);
				System.out.println("[SC] Deux joueurs connectés, création du salon " + id + " sur le port " + port + ".");
				
				out1.println("p/" + port);
				out2.println("p/" + port);
				new ServeurBatailleNavale(id,port).start();
				port++;
				id++;
			}
		} catch(Exception e) {

		}

	}
}
