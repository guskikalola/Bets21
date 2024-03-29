package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erabiltzailea;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private static LoginGUI frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		
		frame =this; 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		lblLogin.setBounds(12, 32, 70, 17);
		contentPane.add(lblLogin);
		
		textField = new JTextField();
		textField.setBounds(163, 78, 114, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		// TODO : Etiketa ( User )
		JLabel lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User")+":"); // "User"
		lblUser.setBounds(90, 80, 55, 14);
		contentPane.add(lblUser);
		
		// TODO : Etiketa ( Password )
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password") + ":"); // "Password"
		lblPassword.setBounds(90, 152, 70, 14);
		contentPane.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String izena = textField.getText();
				String pasahitza = passwordField.getText();
				BLFacade facade = MainGUI.getBusinessLogic();
				
				Erabiltzailea e = facade.existitzenDa(izena, pasahitza);
				
				if(e != null) {
					String rola = e.getRola();
					// TODO: Ireki interfaze berria
					System.out.println(e);
					if(rola.equals(Erabiltzailea.ERABILTZAILEA)) {
						frame.setVisible(false);
						ErabiltzaileGUI era= new ErabiltzaileGUI(e);
						era.setVisible(true);
					}else if(rola.equals(Erabiltzailea.ADMIN)) {
						frame.setVisible(false);
						AdministratzaileGUI adm= new AdministratzaileGUI(e);
						adm.setVisible(true);
					}
				} else {
				
					System.out.println("Errorea");
					// Errorea
				}
				
			}
		});
		btnLogin.setBounds(163, 222, 114, 24);
		contentPane.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(163, 150, 114, 18);
		contentPane.add(passwordField);
		
		// TODO : Etiketa ( Register )
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register")); // "Register"
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				RegisterGUI rg = new RegisterGUI();
				rg.setVisible(true);
			}
		});
		btnRegister.setBounds(321, 48, 98, 24);
		contentPane.add(btnRegister);
		
		// TODO : Etiketa ( NoAccount )
		JLabel lblEzDaukazuKonturik = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NoAccount")); // "No account?"
		lblEzDaukazuKonturik.setBounds(294, 32, 168, 14);
		contentPane.add(lblEzDaukazuKonturik);
		
		// TODO: Etiketa ( Guest )
		JButton btnGuest = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Guest")); // "Guest"
		btnGuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				FindQuestionsGUI fq = new FindQuestionsGUI();
				fq.setVisible(true);
			}
		});
		btnGuest.setBounds(321, 222, 98, 24);
		contentPane.add(btnGuest);
	}
}
