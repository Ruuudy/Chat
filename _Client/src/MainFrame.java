import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void login(String log, String pass){
		String login = log;
		String password = pass;
		String host = "127.0.0.1";
		int port = 8080;
		Socket clientSocket;
		PrintWriter out;
		BufferedReader in;

		
		try{
			clientSocket = new Socket(host, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println(login);
			out.println(password);
			if(in.readLine().equals("ok") ){
				System.out.println("Zalogowano");
				new ChatClient(clientSocket, login);
				dispose();
			}
			
		} catch(IOException e){
			System.exit(1);
		}
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String login = textField.getText();
				String password = textField_1.getText();
				login(login, password);
			}
		});
		btnNewButton.setBounds(155, 206, 146, 34);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(188, 52, 146, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(188, 114, 146, 34);
		contentPane.add(textField_1);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(141, 61, 56, 16);
		contentPane.add(lblLogin);
		
		JLabel lblHaso = new JLabel("Has\u0142o:");
		lblHaso.setBounds(141, 123, 56, 16);
		contentPane.add(lblHaso);
	}
}

