package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Blokeoa;
import domain.Erabiltzailea;
import domain.Pertsona;

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
	private JLabel lblErrorea;
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
		
		frame = this; 
		
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
		lblUser.setBounds(68, 80, 92, 14);
		contentPane.add(lblUser);
		
		// TODO : Etiketa ( Password )
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password") + ":"); // "Password"
		lblPassword.setBounds(68, 152, 92, 14);
		contentPane.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorea.setText(" ");
				String izena = textField.getText();
				String pasahitza = passwordField.getText();
				BLFacade facade = MainGUI.getBusinessLogic();
				
				Pertsona e = facade.existitzenDa(izena, pasahitza);
				
				if(e != null) {
					MainGUI.setLoginErabiltzailea(e);
					MainGUI.eguneratuHistorala(frame);
					System.out.println(e.getClass().getName());
					if(e instanceof Erabiltzailea) {
						Erabiltzailea erabil= facade.getErabiltzailea(e.getIzena());
						if(erabil.getBlokeoa()!=null) {
							frame.setVisible(false);
							BlokeoGUI bl= new BlokeoGUI();
							bl.setVisible(true);
						}else {
							frame.setVisible(false);
							ErabiltzaileGUI era= new ErabiltzaileGUI((Erabiltzailea) e);
							era.setVisible(true);
						}
					}else if(e instanceof Admin) {
						frame.setVisible(false);
						AdministratzaileGUI adm= new AdministratzaileGUI((Admin) e);
						adm.setVisible(true);
					}
				} else {
					lblErrorea.setText(ResourceBundle.getBundle("Etiquetas").getString("errorea_pasahitza_ez_zuzena"));
				}
				
			}
		});
		btnLogin.setBounds(163, 206, 114, 24);
		contentPane.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(163, 150, 114, 18);
		contentPane.add(passwordField);
		
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register")); // "Register"
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				MainGUI.eguneratuHistorala(frame);
				RegisterGUI rg = new RegisterGUI();
				rg.setVisible(true);
			}
		});
		btnRegister.setBounds(304, 48, 115, 24);
		contentPane.add(btnRegister);
		
		JLabel lblEzDaukazuKonturik = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NoAccount")); // "No account?"
		lblEzDaukazuKonturik.setBounds(294, 32, 168, 14);
		contentPane.add(lblEzDaukazuKonturik);
		
		JButton btnGuest = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Guest")); // "Guest"
		btnGuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				MainGUI.eguneratuHistorala(frame);
				FindQuestionsGUI fq = new FindQuestionsGUI();
				fq.setVisible(true);
			}
		});
		btnGuest.setBounds(304, 206, 115, 24);
		contentPane.add(btnGuest);
		
		JButton atzeraEgin = new JButton("<");
		atzeraEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				JFrame atzekoa = MainGUI.atzeraEgin();
				frame.setVisible(false);
				atzekoa.setVisible(true);
			}
		});
		atzeraEgin.setBounds(12, 0, 41, 27);
		contentPane.add(atzeraEgin);
		
		lblErrorea = new JLabel();
		lblErrorea.setBounds(28, 242, 362, 17);
		contentPane.add(lblErrorea);
	}
	@Override
	public void setVisible(boolean a) {
		super.setVisible(a);
		lblErrorea.setText("");
	}
}
