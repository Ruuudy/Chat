
public class Server {
	public static void main(String[] args){
		System.out.print("Jestem serwerem");
		DBConnect connect = new DBConnect();
		SocketServer s = new SocketServer();
		s.runServer(connect);
	}
}
