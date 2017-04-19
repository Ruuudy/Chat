import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;



public class ServerRunnable implements Runnable {
	
	private Socket clientSocket = null;
	private String clientName = null;
	private DBConnect connect;
	private final ServerRunnable[] clients;
	BufferedReader in = null;
	PrintWriter  out = null;
	
	public ServerRunnable(Socket client, ServerRunnable[] clients, DBConnect connect){
		this.clientSocket = client;
		this.clients = clients;
		this.connect = connect;
	}
	
	public void run(){
		try{
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			String log = in.readLine();
			String pass = in.readLine();
			if(connect.contains(log, pass) == 1){
				this.out.println("ok");
				this.out.println("Witam u¿ytkownika: " + log);
				synchronized (this) {
					for(int i = 0; i < clients.length; i++){
						System.out.println(clients[i]);
						if(clients[i]!= null && clients[i] == this){
							clientName = "@" + log;
						}
					}
				}
				while(true){
					String line = in.readLine();
					if(line.contains("close_chat")){
						System.out.println("Zamk³em");
						break;
					}
					else if(line.startsWith("@")){
						String[] words = line.split("\\s", 2);
						String user = words[0];
						String massage = words[1];
						if(massage != null){
							massage = massage.trim();
							synchronized(this){
								for(int i = 0; i< clients.length; i++){
									if(clients[i].clientName.equals(user) && clients[i]!=null){
										clients[i].out.println("User " + log + " send private massage: " + massage);
										this.out.println("Message " + massage + " send to " + clients[i].clientName);
										break;
									}
								}
							}	
						}
					}
					else{
						synchronized (this) {
							for(int i = 0; i < clients.length; i++){
								if(clients[i]!=null){
									clients[i].out.println("From: " + log + " - " + line);
								}
							}
						}
					}
				}
				
				
			        for (int i = 0; i < clients.length; i++) {
			          if (clients[i] != null && clients[i] != this && clients[i].clientName != null) {
			            clients[i].out.println("+++ U¿ytkownik: " + this.clientName + " opuœci³ czat +++");
			          }
			        }
				
				synchronized (this) {
			        for (int i = 0; i < clients.length; i++) {
			          if (clients[i] == this) {
			            clients[i] = null;
			          }
			        }
				}
				out.close();
				in.close();
				clientSocket.close();
			}
			
		} catch(IOException e){
			System.out.println(e.getMessage());
		} 
	}
}
