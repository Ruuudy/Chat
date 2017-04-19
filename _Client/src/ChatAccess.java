import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

public class ChatAccess extends Observable {
        private PrintWriter output;
        private Socket socket;

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /** Create socket, and receiving thread */
        public void InitSocket(Socket client) throws IOException {
            socket = client;
        	output = new PrintWriter(socket.getOutputStream(),true);
       

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null)
                            notifyObservers(line);
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }


        /** Send a line of text */
        public void send(String text) {
           
                output.println(text);
                output.flush();
           
        }

        /** Close the socket */
        public void close() {
            try {
            	output.println("close_chat");
            	socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
}