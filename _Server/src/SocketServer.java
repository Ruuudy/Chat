import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	private static final int maxClients = 4;
	private static final ServerRunnable[] clients = new ServerRunnable[maxClients];
	int port = 8080;
	ServerSocket socket = null;
	
	public void runServer(DBConnect connect){
		try{
			socket = new ServerSocket(port);
			
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		while(true){
			try{
				Socket clientSocket = socket.accept();
				for(int i = 0; i < maxClients; i++){
					if(clients[i] == null){
						new Thread(clients[i] = new ServerRunnable(clientSocket, clients, connect)).start();
						break;
					}
				}
			} catch(IOException e){
				System.out.println(e.getMessage());
			}
		}
	}
}
