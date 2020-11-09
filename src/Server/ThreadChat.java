package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadChat extends Thread {
	int id;
	BufferedReader in1,in2;
	PrintWriter out1,out2;
	static int nbid=0;
	
	public void ecrire(String msg) {
		out1.println(msg);
		out2.println(msg);
	}
	
	
	public ThreadChat(int id, Socket client1, Socket client2) {
		try {
			this.id = id;
			nbid++;
			in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			out1 = new PrintWriter(client1.getOutputStream(), true);
			ecrire("Id de la partie="+id+"\n");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void run() {
		try {
			while (true) {
				String message1=in1.readLine();
				out2.println(message1);
				String message2=in1.readLine();
				out2.println(message2);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}