import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class WindowFrame {

	private JFrame frmGrupaTeamspeakMessenger;
	private JTextField textField;
	public String nick = null;
	JLabel lbl_shownick = new JLabel();
	String address = "localhost";
	Integer portNumber = 3058;
	Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;
	JTextArea panel_1;
	Thread connection;
	private static boolean connected = false;

	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowFrame window = new WindowFrame();
					
					window.frmGrupaTeamspeakMessenger.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowFrame() {
		connection = new Thread(new MSGConnection());
		connection.start();
		initialize();
	}
	
	private void send_message(String message) {
		out.println(message);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGrupaTeamspeakMessenger = new JFrame();
		frmGrupaTeamspeakMessenger.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Czoks\\Pictures\\C++ obrazy\\ciuchcia80x80.png"));
		frmGrupaTeamspeakMessenger.setTitle("Grupa TeamSpeak Messenger");
		frmGrupaTeamspeakMessenger.setBounds(100, 100, 710, 439);
		frmGrupaTeamspeakMessenger.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGrupaTeamspeakMessenger.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(522, 32, 160, 256);
		frmGrupaTeamspeakMessenger.getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new JTextArea();
		panel_1.setEditable(false);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(19, 32, 491, 256);
		frmGrupaTeamspeakMessenger.getContentPane().add(panel_1);
		
		JLabel lblOknoJebawcze = new JLabel("Okno jebawcze:");
		lblOknoJebawcze.setBounds(19, 12, 90, 16);
		frmGrupaTeamspeakMessenger.getContentPane().add(lblOknoJebawcze);
		
		JLabel lblListaPrzyjebw = new JLabel("Lista przyjeb\u00F3w:");
		lblListaPrzyjebw.setBounds(522, 12, 92, 16);
		frmGrupaTeamspeakMessenger.getContentPane().add(lblListaPrzyjebw);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(19, 300, 491, 83);
		frmGrupaTeamspeakMessenger.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setToolTipText("Wpisz prosze jakie\u015B wulgaryzmy.");
		textField.setBounds(0, 28, 387, 26);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("ZJEB");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send_message(textField.getText().toString());
				panel_1.append(textField.getText().toString());
				textField.setText("");
			}
		});
		btnNewButton.setBounds(399, 28, 80, 26);
		panel_2.add(btnNewButton);
		
		JLabel lblNick = new JLabel("Nick:");
		lblNick.setBounds(522, 300, 55, 16);
		frmGrupaTeamspeakMessenger.getContentPane().add(lblNick);
		
		String nick = (String)JOptionPane.showInputDialog("Twój nick:");
		this.nick = nick;
		lbl_shownick.setText(nick);
		lbl_shownick.setBounds(522, 328, 55, 16);
		frmGrupaTeamspeakMessenger.getContentPane().add(lbl_shownick);
	}
	
	
	private class MSGConnection implements Runnable{
		
		public MSGConnection() {
			try {
				socket = new Socket(address, portNumber);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while(true) {
				try {
					out = new PrintWriter(socket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					panel_1.append(in.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}
