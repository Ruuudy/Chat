import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
 class ChatClient extends JFrame implements Observer {
		JTextArea textArea;
		JTextArea textArea_1;
		static String title;
		private ChatAccess chatAccess;
		private JPanel contentPane;
	 	static Socket clientSocket;
	
		public ChatClient(Socket clientSocket, String login){
			this.clientSocket = clientSocket;
			this.title = login;
			main();
		}
		
		/**
		 * @wbp.parser.constructor
		 */
		public ChatClient(ChatAccess chatAccess) {

			Border blackline = BorderFactory.createLineBorder(Color.black);
			this.chatAccess = chatAccess;
			chatAccess.addObserver(this);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 647, 480);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setBorder(blackline);
			textArea.setBounds(5, 5, 479, 339);
			contentPane.add(textArea);
			
			JButton btnNewButton = new JButton("Exit");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chatAccess.close();
					dispose();
				}
			});
			btnNewButton.setBounds(489, 388, 136, 40);
			contentPane.add(btnNewButton);
			
			JButton btnSend = new JButton("Send");
			btnSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String massage = textArea_1.getText();
	                if (massage != null && massage.trim().length() > 0)
	                        chatAccess.send(massage);
	                textArea_1.selectAll();
	                textArea_1.setText("");
				}
			});
			btnSend.setBounds(489, 343, 136, 40);
			contentPane.add(btnSend);
			
			textArea_1 = new JTextArea();
			textArea_1.setBorder(blackline);
			textArea_1.setBounds(5, 360, 479, 68);
			contentPane.add(textArea_1);
			
			this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                    dispose();
                }
			});
		}
		
		public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textArea.append(finalArg.toString());
                    textArea.append("\n");
                }
            });
		}
	
	
	private static void main() {
		ChatAccess access = new ChatAccess();
		System.out.println("ok");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new ChatClient(access);
			        frame.setTitle("Chat " + title);
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			       
			        frame.setResizable(true);
			        frame.setVisible(true);
			        access.InitSocket(clientSocket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
}
